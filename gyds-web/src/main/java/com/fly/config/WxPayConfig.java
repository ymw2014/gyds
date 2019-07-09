package com.fly.config;

import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WxPayService.class)
public class WxPayConfig {
    @Value("${const.APPID}")
    private String appid;
    @Value("${const.MCHID}")
    private String mchid;
    @Value("${const.KEY}")
    private String key;
    @Value(("${const.notifyUrl}"))
    private String notifyUrl;
    @Value("${const.keyPath}")
    private String keyPath;
    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
        com.github.binarywang.wxpay.config.WxPayConfig payConfig = new com.github.binarywang.wxpay.config.WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(appid));
        payConfig.setMchId(StringUtils.trimToNull(mchid));
        payConfig.setMchKey(StringUtils.trimToNull(key));
//        payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getKeyPath()));
        payConfig.setNotifyUrl(notifyUrl);
        payConfig.setTradeType("JSAPI");
        payConfig.setKeyPath(keyPath);
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
