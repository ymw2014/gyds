package com.fly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fly.domain.UploadDo;

/**
 * 获取配置文件上传路径
 */
@Component
@ConfigurationProperties(prefix="filedo")
public class FileUploadConfig extends UploadDo {
}
