package com.fly.wxpay.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import com.github.wxpay.sdk.WXPayConfig;

public class IWxPayConfig implements  WXPayConfig{

	
	  @Value("${wxpay.appid}") 
	  private String app_id;
	  
	  @Value("${wxpay.key}") 
	  private String wx_pay_key;
	  
	  @Value("${wxpay.mch_id}") 
	  private String wx_pay_mch_id;
	 
	private byte[] certData;
	
	public  IWxPayConfig() throws Exception{
        String certPath = "/web/token/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }
	
	@Override
	public String getAppID() {
		return app_id;
	}

	@Override
	public String getMchID() {
		 return wx_pay_mch_id;
	}

	@Override
	public String getKey() {
		 return wx_pay_key;
	}

	@Override
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;

	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 10000;

	}

	
    
    
}
