package com.fly.wxpay.service;

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
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.domain.UserDO;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.sys.domain.SetupDO;
import com.fly.sys.service.SetupService;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.R;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;

@Service
public class PayService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private SetupService setupService;
	
	/**
	 * 创建订单
	 * @param fee 金额
	 * @param expIncType 
	 * @return
	 */
	public R createOrder(String fee, Integer expIncType) {
		OrderDO order = new OrderDO();
		order.setCashOutType(1);
		order.setCashUpType(1);
		order.setExamineStatus(2);
		order.setExpIncType(expIncType);
		order.setOrderNumber(new Date().getTime() + "");
		order.setOrderType(2);
		order.setPrice(new BigDecimal(fee));
		order.setUserId(ShiroUtils.getUserId());
		order.setBusinessTime(new Date());
		order.setIsDel(0);
		Integer orderNum = 0;
		R r = new R();
		try {
			if(orderService.save(order)>0){
				orderNum = order.getId();
			}
			if (orderNum > 0) {
				OrderDO orderDO = orderService.get(orderNum);
				r.put("code", 0);
				r.put("msg", orderDO.getOrderNumber());
				return r;
			}
		}catch(Exception e) {
			e.printStackTrace();
			r.put("code", -1);
			r.put("msg", "未知错误");
		}
		return r;
	}
	
	
	/**
	 * 订单查询
	 * @param orderNum 订单号
	 * @return
	 */
	public R queryOrder(String orderNum) {
		Map<String,Object> pararm = new HashMap<String, Object>();
		pararm.put("orderNumber",orderNum);
		R r = new R();
		try {
			List<OrderDO> list = orderService.list(pararm);
			if (!CollectionUtils.isEmpty(list)) {
				Integer examineStatus = list.get(0).getExamineStatus();
				r.put("code", 0);
				r.put("msg", examineStatus);
				return r;
			}
		}catch(Exception e) {
			e.printStackTrace();
			r.put("code", -1);
			r.put("msg", "未知错误");
		}
		return r;
	} 
	
	
	/**
	 * 提现下单
	 * @param amount
	 * @param orderNum
	 */
	public void cashout(Integer amount, OrderDO orderNew) {
		logger.info("进入提现方法:  提现金额  {}     ,    订单号 ： {}",amount,orderNew.getOrderNumber());
		String wxUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; //获取退款的api接口
    	try {
    		BigDecimal orderPrice = new BigDecimal((amount * 100) + "");;
    		if (amount > 50) {
    			SetupDO setupDO = setupService.get(1);
    			BigDecimal toltal = new BigDecimal((amount * 100) + "");
    			BigDecimal withdrawalFee = new BigDecimal(setupDO.getWithdrawalFee() + "");
    			BigDecimal multiply = toltal.multiply(withdrawalFee);
    			orderPrice = toltal.subtract(multiply);
    		}
    		IWxPayConfig config = new IWxPayConfig();
    		WXPay wxpay = new WXPay(config);
    		UserDO userDO2 = userService.get(orderNew.getUserId());
    		SortedMap<String, String> packageParams = new TreeMap<String, String>(); 
	        packageParams.put("nonce_str",WXPayUtil.generateNonceStr());  //随机生成后数字，保证安全性
	        packageParams.put("partner_trade_no",orderNew.getOrderNumber()); //生成商户订单号
	        packageParams.put("openid",userDO2.getOpenId());            // 支付给用户openid
	        packageParams.put("check_name","NO_CHECK");    //是否验证真实姓名呢
	        packageParams.put("amount",orderPrice.intValue() + "");            //企业付款金额，单位为分
	        packageParams.put("desc","余额提现");                //企业付款操作说明信息。必填。
	        packageParams.put("spbill_create_ip",localIp()); //调用接口的机器Ip地址
	        packageParams.put("mchid", config.getMchID());   
	        packageParams.put("mch_appid", config.getAppID());
	        packageParams.put("sign", WXPayUtil.generateSignature(packageParams, config.getKey()));
	        String mapToXml = WXPayUtil.mapToXml(packageParams);
	        logger.info("进入提现方法:  请求信息信息  {}  ",mapToXml);
    		String xmlData = wxpay.requestWithCert(wxUrl, packageParams, config.getHttpConnectTimeoutMs(), config.getHttpReadTimeoutMs());
    		logger.info("进入提现方法:  返回信息  {}  ",xmlData);
    		Map<String, String> xmlToMap = WXPayUtil.xmlToMap(xmlData);
    		 if("SUCCESS".equals(xmlToMap.get("return_code")) ){
    			 if ("SUCCESS".equals(xmlToMap.get("result_code"))) {
    				 //TODO 执行成功付款后的业务逻辑
    				 String partnerTradeNo = xmlToMap.get("partner_trade_no");
    				 Map<String,Object> pararm = new HashMap<String, Object>();
    				 pararm.put("orderNumber",partnerTradeNo);
    				 List<OrderDO> list = orderService.list(pararm);
    				 if (!CollectionUtils.isEmpty(list)) {
    					 OrderDO orderDO = list.get(0);
    					 orderDO.setExamineStatus(1);
    					 int update = orderService.update(orderDO);
    					 if (update > 0) {
    						 UserDO userDO = userService.get(orderDO.getUserId());
    						 logger.info("获取用户信息:    {}",userDO.toString());
    						 BigDecimal account = userDO.getAccount();
    						 if (account == null) {
    							 account = new BigDecimal(0);
    						 }
    						 BigDecimal add = account.subtract(orderDO.getPrice());
    						 userDO.setAccount(add);
    						 userService.update(userDO);
    						 logger.info("订单状态修改成功");
    					 }else {
    						 logger.info("订单状态修改失败");
    					 }
    				 }else {
    					 logger.info("订单状查询失败");
    				 }
    			 }else {
    				 logger.info("提现失败：{}",xmlToMap.get("err_code_des"));
    			 }
             }else{
            	 logger.info("提现失败");
             }
    	}catch(Exception e) {
    		logger.info("提现异常 :  {}", e.getMessage());
    	}
	}
	
	
	/**
	 * 获取ip地址
	 * @return
	 */
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
	
}
