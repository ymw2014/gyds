package com.fly.wxpay.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.common.controller.BaseController;
import com.fly.domain.UserDO;
import com.fly.index.utils.OrderType;
import com.fly.order.domain.OrderDO;
import com.fly.order.service.OrderService;
import com.fly.system.service.UserService;
import com.fly.utils.R;
import com.fly.wxpay.service.IWxPayConfig;
import com.github.binarywang.utils.qrcode.MatrixToImageWriter;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


@Controller
@RequestMapping("/wxpay")
public class WxPayController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	/**
	     * 创建订单
	 * @param param
	 * @return
	 */
	@RequestMapping("/createOrder")
	@ResponseBody
	public R createOrder(@RequestParam Map<String, Object> param) {
		String fee = param.get("data").toString();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderType", 2);
		params.put("expIncType", OrderType.CHONG_ZHI);
		params.put("examineStatus", 2);
		params.put("cashUpType", 1);
		params.put("cashOutType", 1);
		params.put("price", fee);
		Integer orderNum = creadOrder(params);
		R r = new R();
		String num = "";
		if (orderNum > 0) {
			OrderDO orderDO = orderService.get(orderNum);
			num = orderDO.getOrderNumber();
			r.put("code", 0);
			r.put("msg", orderDO.getOrderNumber());
			logger.info("pc  createOrder id:{}, orderNum:{}",orderNum, num);
			return r;
		}
		r.put("code", -1);
		r.put("msg", "未知错误");
		return r;
	}
	
	/**
	     * 查询订单状态
	 * @param orderNum 订单号
	 * @return
	 */
	@RequestMapping("/queryOrder")
	@ResponseBody
	public R queryOrder(String orderNum) {
		Map<String,Object> pararm = new HashMap<String, Object>();
		pararm.put("orderNumber",orderNum);
		List<OrderDO> list = orderService.list(pararm);
		R r = new R();
		if (!CollectionUtils.isEmpty(list)) {
			Integer examineStatus = list.get(0).getExamineStatus();
			r.put("code", 0);
			r.put("msg", examineStatus);
			return r;
		}
		r.put("code", -1);
		r.put("msg", "未知错误");
		return r;
	} 
	
	/**
	 * pc扫码支付
	 * @param response
	 * @param request
	 * @param data 金额
	 * @param orderNum 订单号
	 * @return
	 */
	@RequestMapping("/pay")
	public String payWeChat(String content, HttpServletResponse response, HttpServletRequest request,
			Integer data,String orderNum) {
		logger.info("进入支付方法，payWeChat");
		try {
			logger.warn("支付金额:  " + data );
			content = this.getImageUrl("余额充值", orderNum, data);
			logger.info("wxpay code_url: " + content);
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Hashtable hints = new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
			response.setContentType("image/jpeg");
			MatrixToImageWriter.writeToStream(bitMatrix, "jpg", response.getOutputStream());
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.warn("支付失败");
		}
		return null;
	}
	
	/**
	 * 支付回调方法
	 * @param request
	 * @param response
	 * @param xmlData
	 * @throws Exception
	 */
	@RequestMapping("/callback")
	public void  callBack(HttpServletRequest request, HttpServletResponse response,String xmlData) throws Exception {
		logger.info("进入微信支付异步通知");
		logger.info("callback() start, notixmlData={}", xmlData);
	    String resXml="";
	    try{
	        //
	        InputStream is = request.getInputStream();
	        //将InputStream转换成String
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	sb.append(line + "\n");
	        }
	        resXml=sb.toString();
	        Map<String,String> result = WXPayUtil.xmlToMap(resXml);
	        String orderNum = "";
	        String err_code_des = "";
	        if("SUCCESS".equals(result.get("return_code"))){
	        	//微信返回状态码为成功
	        	err_code_des = result.get("err_code_des");
		        if ("SUCCESS".equals(result.get("result_code"))) { //支付成功改变订单状态
	        		orderNum = result.get("out_trade_no");//获取订单号
	        		logger.info("pc  callback() orderNumber {}", orderNum);
	        		Map<String,Object> pararm = new HashMap<String, Object>();
	        		pararm.put("orderNumber",orderNum);
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
	        				BigDecimal add = account.add(orderDO.getPrice());
	        				userDO.setAccount(add);
	        				userService.update(userDO);
	        				logger.info("订单状态修改成功");
	        			}else {
	        				 logger.info("订单状态修改失败");
	        			}
	        		}else {
	        			logger.info("订单状查询失败");
	        		}
		        
	            }else{
	                //业务结果状态码为失败
	            	logger.info("***************支付平台订单ID:"+orderNum,"err_code_des="+err_code_des);
	            }
	        }else{
	        	
	        	//微信返回状态码为失败
	        	logger.info("***************支付平台订单ID:"+orderNum+"查询微信支付接口异常:  {}", err_code_des);

	        }
	
	        logger.info("微信支付异步通知请求包: {}", resXml);
	        response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/xml; charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.print(payBack(resXml));//方法返回值是void，然后用response输出的,不然回调方法会一直重复执行
	        out.close();
	        
	    }catch (Exception e){
	        logger.error("微信支付回调通知失败",e);
	        String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
	      
	    }
	}
	
	public String payBack(String notifyData) throws Exception {
		logger.info("payBack() start, notifyData={}", notifyData);
	    String xmlBack="";
	    Map<String, String> notifyMap = null;
	    try {
	    	IWxPayConfig config = new IWxPayConfig();
			WXPay wxpay = new WXPay(config);

	        notifyMap = WXPayUtil.xmlToMap(notifyData);         // 转换成map
	        if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
	            // 签名正确
	            // 进行处理。
	            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
	            String return_code = notifyMap.get("return_code");//状态
	            String out_trade_no = notifyMap.get("out_trade_no");//订单号

	            if (out_trade_no == null) {
	                logger.info("微信支付回调失败订单号: {}", notifyMap);
	                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
	                return xmlBack;
	            }

	            // 业务逻辑处理 ****************************
	            logger.info("微信支付回调成功订单号: {}", notifyMap);
	            xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
	            return xmlBack;
	        } else {
	            logger.error("微信支付回调通知签名错误");
	            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
	            return xmlBack;
	        }
	    } catch (Exception e) {
	        logger.error("微信支付回调通知失败",e);
	        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
	    }
	    return xmlBack;
	}

	/**
	 * 获取支付二维码路径
	 * @param body
	 * @param out_trade_no
	 * @param total_fee
	 * @return
	 * @throws Exception
	 */
	public String getImageUrl(String body, String out_trade_no, Integer total_fee) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		IWxPayConfig config = new IWxPayConfig();
		WXPay wxpay = new WXPay(config);
		String code_url = "";
		paramMap.put("body", body); // 商品描述
		paramMap.put("notify_url", "http://zhgy.61966.com/wxpay/callback"); // 支付成功后，回调地址
		paramMap.put("out_trade_no", out_trade_no); // 商户订单号
		paramMap.put("spbill_create_ip", this.localIp()); // 本机的Ip
		paramMap.put("total_fee", total_fee  + ""); // 金额必须为整数 单位为分
		paramMap.put("trade_type", "NATIVE"); // 交易类型

		try {
			Map<String, String> resp = wxpay.unifiedOrder(paramMap);
			code_url = resp.get("code_url");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return code_url;
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
