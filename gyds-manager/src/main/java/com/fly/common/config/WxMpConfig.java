package com.fly.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Configuration
public class WxMpConfig {
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.token}")
    private String token;
    private static final Logger log= LoggerFactory.getLogger(WxMpConfig.class);
    @Bean
    public WxMpService wxMpService(){
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(appid); // 设置微信公众号的appid
        config.setSecret(secret); // 设置微信公众号的app corpSecret
        config.setToken(token); // 设置微信公众号的token
//        config.setAesKey("..."); // 设置微信公众号的EncodingAESKey
        log.info("*****************************实例化WxMpService");
        WxMpService wxService = new WxMpServiceImpl();// 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
        wxService.setWxMpConfigStorage(config);
        return wxService;

    }

}
