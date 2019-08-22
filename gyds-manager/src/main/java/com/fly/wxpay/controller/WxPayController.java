package com.fly.wxpay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class WxPayController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	

	@RequestMapping("/pay")
	public String payWeChat(String ids, String content, HttpServletResponse response, HttpServletRequest request,
			ModelMap model, String totalFee) {
		logger.info("进入支付方法，payWeChat");
		try {
			content = this.getImageUrl("余额充值", new Date().getTime() + "", "1");
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
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
			/* 返回成功的标志
			 * <result_code><![CDATA[SUCCESS]]></result_code>
			 * <return_code><![CDATA[SUCCESS]]></return_code>
			 */

	        resXml=sb.toString();
	        Map<String,String> result = WXPayUtil.xmlToMap(resXml);
	        if ("SUCCESS".equals(result.get("result_code"))) { //支付成功改变订单状态
	        	
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

	public String getImageUrl(String body, String out_trade_no, String total_fee) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		IWxPayConfig config = new IWxPayConfig();
		WXPay wxpay = new WXPay(config);
		String code_url = "";
		paramMap.put("body", body); // 商品描述
		paramMap.put("notify_url", "http://zhgy.61966.com/wxpay/callback"); // 支付成功后，回调地址
		paramMap.put("out_trade_no", out_trade_no); // 商户订单号
		paramMap.put("spbill_create_ip", this.localIp()); // 本机的Ip
		paramMap.put("total_fee", total_fee); // 金额必须为整数 单位为分
		paramMap.put("trade_type", "NATIVE"); // 交易类型

		try {
			Map<String, String> resp = wxpay.unifiedOrder(paramMap);
			System.out.println(resp);
			code_url = resp.get("code_url");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return code_url;
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

	
}
