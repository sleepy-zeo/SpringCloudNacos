package com.lullaby.ssjr.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "redis")
public class JedisProperties {

    private String host;
    private int port;
    private String password;
    private int timeout;
    private Pool pool;

    @Data
    public static class Pool {
        private int maxActive;
        private int maxWait;
        private int maxIdle;
        private int minIdle;
    }
}
