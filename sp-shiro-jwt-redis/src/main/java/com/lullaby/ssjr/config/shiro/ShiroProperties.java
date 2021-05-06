package com.lullaby.ssjr.config.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {
}
