package com.fly.wxpay.controller;

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

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fly.domain.UserDO;
import com.fly.system.utils.ShiroUtils;
import com.fly.wxpay.service.IWxPayConfig;
import com.fly.wxpay.utils.HttpUtils;
import com.github.wxpay.sdk.WXPayUtil;

@Controller
@RequestMapping("/app/wxpay")
public class AppWxPayController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	@RequestMapping("/pay")
	public String pay(Integer totalFee) throws Exception {
		String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		IWxPayConfig config = new IWxPayConfig();
		UserDO user = ShiroUtils.getUser();
		Map<String, String> data = new HashMap<String, String>();
        data.put("appid", config.getAppID());//微信支付分配的公众账号ID（企业号corpid即为此appId）
        data.put("mch_id", config.getMchID());//微信支付分配的商户号
        String  str = WXPayUtil.generateNonceStr();
        data.put("nonce_str", str); // 通过微信工具类生成  随机字符串
        data.put("body", "余额充值");//商品描述
        data.put("out_trade_no", new Date().getTime() + ""); // 订单唯一编号, 不允许重复
        data.put("total_fee", String.valueOf(totalFee * 100)); // 订单金额, 单位分
        data.put("spbill_create_ip", localIp()); // 下单ip
        data.put("openid", user.getOpenId()); // 微信公众号统一标示openid
        data.put("notify_url", "URL这里填写，你的回调域名"); // 订单结果通知, 微信主动回调此接口
        data.put("trade_type", "SAPI"); // 固定填写
        // 生成带有 sign 的 XML 格式字符串
        String xmlparam = WXPayUtil.generateSignedXml(data, config.getKey());
        // 发送请求
        String resultStr = HttpUtils.postData(unifiedorder_url, xmlparam);
        logger.info("appPay 返回信息 :  " + resultStr);
        
		return "";
	}
	
	/*
	 * public Map<String,String> generateSigned(String resultStr) { return }
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
