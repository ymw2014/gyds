package com.fly.common.config;

import com.fly.domain.UploadDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 路径映射
 */
@Component
class WebConfigurer implements WebMvcConfigurer {//WebMvcConfigurerAdapter 已过时
	@Autowired
	UploadDo bootdoConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/files/**")
				.addResourceLocations("file:///"+bootdoConfig.getUploadPath());
	}

}