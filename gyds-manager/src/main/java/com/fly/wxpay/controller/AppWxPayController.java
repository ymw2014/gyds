package com.fly.wxpay.controller;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fly.common.controller.BaseController;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;
import com.fly.wxpay.service.IWxPayConfig;
import com.fly.wxpay.utils.HttpUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;

@Controller
@RequestMapping("/app/wxpay")
public class AppWxPayController extends BaseController{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/pay")
	@ResponseBody
	public Map<String, String> pay(Integer totalFee) throws Exception {
        try {
        	IWxPayConfig config = new IWxPayConfig();
        	WXPay wxpay = new WXPay(config);
        	UserDO user = ShiroUtils.getUser();
        	Map<String, String> data = new HashMap<String, String>();
        	String orderNum = createOrder(totalFee+ "");
        	UserDO userDO = userService.get(user.getUserId());
        	data.put("body", "余额充值");//商品描述
        	data.put("out_trade_no", orderNum); // 订单唯一编号, 不允许重复
        	data.put("total_fee", totalFee+ ""); // 订单金额, 单位分
        	data.put("spbill_create_ip", localIp()); // 下单ip
        	data.put("openid", userDO.getOpenId()); // 微信公众号统一标示openid
        	data.put("notify_url", "http://zhgy.61966.com/app/wxpay/callback"); // 订单结果通知, 微信主动回调此接口
        	data.put("trade_type", "JSAPI"); // 固定填写
        	JSONObject dataInfo = new JSONObject();
        	dataInfo.put("data", data);
        	logger.info("user 信息:  " + userDO.getOpenId());
        	logger.info("appPay 参数 :  " + dataInfo.toString());
        	Map<String, String> resp = wxpay.unifiedOrder(data);
        	dataInfo.put("resp", resp);
        	logger.info("appPay 返回信息 :  " + resp);
        	logger.info("appPay 支付订单号 :  {}", orderNum);
        	if ("SUCCESS".equals(resp.get("result_code") ) &&  "SUCCESS".equals(resp.get("return_code"))){//result_code=SUCCESS, mch_id=1309497501, return_code=SUCCESS
        		Map<String, String> prepayId = getPrepayId(config,resp.get("prepay_id"));
        		prepayId.put("orderNum", orderNum);
        		return prepayId;
        	}
        }catch(Exception e) {
        	logger.info("支付异常 :  {}", e.getMessage());
        }
        
		return null;
	}
	
	public String  createOrder(String fee) throws Exception{
		BigDecimal f = new BigDecimal(fee);
		BigDecimal divide = f.divide(new BigDecimal(100));
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderType", 2);
		params.put("expIncType", OrderType.CHONG_ZHI);
		params.put("examineStatus", 2);
		params.put("cashUpType", 1);
		params.put("cashOutType", 1);
		params.put("price", divide);
		Integer orderNum = creadOrder(params);
		R r = new R();
		if (orderNum > 0) {
			OrderDO orderDO = orderService.get(orderNum);
			return orderDO.getOrderNumber();
		}
		return "";
	}
	
	@RequestMapping("/orderUpdate")
	@ResponseBody
	public R queryOrder(String orderNum) {
		logger.info("appPay orderUpdate 支付订单号 :  {}", orderNum);
		Map<String,Object> pararm = new HashMap<String, Object>();
		pararm.put("orderNumber",orderNum);
		List<OrderDO> list = orderService.list(pararm);
		R r = new R();
		int update = -1;
		if (!CollectionUtils.isEmpty(list)) {
			OrderDO orderDO = list.get(0);
			orderDO.setExamineStatus(1);
			update = orderService.update(orderDO);
			if (update > 0) {
				UserDO userDO = userService.get(orderDO.getUserId());
				logger.info("获取用户信息:    {}",userDO.toString());
				BigDecimal account = userDO.getAccount();
				if (account == null) {
					account = new BigDecimal(0);
				}
				BigDecimal add = account.add(orderDO.getPrice());
				userDO.setAccount(add);
				userService.update(userDO);
				logger.info("订单状态修改成功");
			}else {
				 logger.info("订单状态修改失败");
			}
		}
		r.put("code", update);
		return r;
	} 
	
	
	private String localIp() {
		String ip = null;
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
				for (InterfaceAddress add : InterfaceAddress) {
					InetAddress Ip = add.getAddress();
					if (Ip != null && Ip instanceof Inet4Address) {
						ip = Ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.warn("获取本机Ip失败:异常信息:" + e.getMessage());
		}
		return ip;
	}
	
	
	public Map<String,String> getPrepayId(IWxPayConfig config,String prepayId) throws Exception {
		String timeStamp = new Long(System.currentTimeMillis()/1000).toString();
        // 创建返回值
        //组装二次签名
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("appId", config.getAppID());
        resultMap.put("timeStamp", timeStamp);
        resultMap.put("nonceStr", WXPayUtil.generateNonceStr());
        resultMap.put("package", "prepay_id=" + prepayId);
        resultMap.put("signType", "MD5");
        // 生成签名
        String paySign = WXPayUtil.generateSignature(resultMap, config.getKey());
        resultMap.put("paySign", paySign);
        return resultMap;
	}
	
}
