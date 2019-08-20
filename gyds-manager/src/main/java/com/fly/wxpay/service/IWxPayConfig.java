package com.fly.wxpay.service;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.wxpay.sdk.WXPayConfig;





@Service
public class IWxPayConfig implements  WXPayConfig{

	/*
	 * @Value("${wxpay.appid}") private String app_id;
	 * 
	 * @Value("${wxpay.key}") private String wx_pay_key;
	 * 
	 * @Value("${wxpay.mch_id}") private String wx_pay_mch_id;
	 */
	
	@Override
	public String getAppID() {
		return null;
	}

	@Override
	public String getMchID() {
		return null;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public InputStream getCertStream() {
		return null;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		// TODO Auto-generated method stub
		return 0;
	}

	
    
    
}
