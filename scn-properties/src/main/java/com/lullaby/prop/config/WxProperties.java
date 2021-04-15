package com.lullaby.prop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxProperties {

    private Long appId;
    private String secret;
    private String token;
}
