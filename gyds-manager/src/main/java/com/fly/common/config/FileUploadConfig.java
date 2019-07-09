package com.fly.common.config;

import com.fly.domain.UploadDo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 获取配置文件上传路径
 */
@Component
@ConfigurationProperties(prefix="filedo")
public class FileUploadConfig extends UploadDo {
}
