package com.mogu.cache.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheConfig {

    // 配置缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 配置指定cacheNames的过期时长
        Map<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();
        cacheConfigMap.put("user", cacheConfiguration(7200));
        cacheConfigMap.put("userById", cacheConfiguration(7200));
        cacheConfigMap.put("menu", cacheConfiguration(7200));
        cacheConfigMap.put("menuById", cacheConfiguration(7200));
        // 配置其它cacheNames的过期时长
        return RedisCacheManager.builder(redisConnectionFactory)
                .withInitialCacheConfigurations(cacheConfigMap)
                .cacheDefaults(cacheConfiguration(3600))
                .build();
    }

    private RedisCacheConfiguration cacheConfiguration(long seconds) {
        // 基本配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                // StringRedisSerializer用于序列化字符串
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // GenericJackson2JsonRedisSerializer用于序列化对象
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }
}
