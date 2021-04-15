package com.lullaby.prop.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WxProperties.class)
public class WxPropertiesConfiguration {
}
