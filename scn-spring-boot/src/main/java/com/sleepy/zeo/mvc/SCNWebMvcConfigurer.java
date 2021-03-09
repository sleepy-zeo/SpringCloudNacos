package com.sleepy.zeo.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SCNWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 静态资源处理，自定义静态资源映射目录
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*.ico").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.jpg").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.jpeg").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/*.png").addResourceLocations("classpath:/static/");
    }

}
