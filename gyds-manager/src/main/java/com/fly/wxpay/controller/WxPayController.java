package com.fly.wxpay.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			content = this.getImageUrl("余额充值", "6578754569698556", totalFee);
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
	public String callBack(HttpServletRequest request, HttpServletResponse response,String xmlData) throws Exception {
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
	        resXml=sb.toString();
	        logger.info("微信支付异步通知请求包: {}", resXml);
	        return payBack(resXml);
	    }catch (Exception e){
	        logger.error("微信支付回调通知失败",e);
	        String result = "-1";
	        return result;
	    }
	}
	
	public String payBack(String notifyData) throws Exception {
	    logger.info("payBack() start, notifyData={}", notifyData);
	    IWxPayConfig config = new IWxPayConfig();
		WXPay wxpay = new WXPay(config);
	    String xmlBack="";
	    Map<String, String> notifyMap = null;
	    try {

	        notifyMap = WXPayUtil.xmlToMap(notifyData);         // 转换成map
	        if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
	            // 签名正确
	            // 进行处理。
	            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
	            String return_code = notifyMap.get("return_code");//状态
	            String out_trade_no = notifyMap.get("out_trade_no");//订单号

	            if (out_trade_no == null) {
	                logger.info("微信支付回调失败订单号: {}", notifyMap);
	                xmlBack = "-1";
	                return xmlBack;
	            }

	            // 业务逻辑处理 ****************************
	            logger.info("微信支付回调成功订单号: {}", notifyMap);
	            xmlBack = "1";
	            return xmlBack;
	        } else {
	            logger.error("微信支付回调通知签名错误");
	            xmlBack = "-1";
	            return xmlBack;
	        }
	    } catch (Exception e) {
	        logger.error("微信支付回调通知失败",e);
	        xmlBack = "-1";
	    }
	    return xmlBack;
	}

	public String getImageUrl(String body, String out_trade_no, String total_fee) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		IWxPayConfig config = new IWxPayConfig();
		WXPay wxpay = new WXPay(config);
		String code_url = "";
		paramMap.put("body", body); // 商品描述
		paramMap.put("notify_url", "http://yqhdb.com/payment/wechat/pay.php"); // 支付成功后，回调地址
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
