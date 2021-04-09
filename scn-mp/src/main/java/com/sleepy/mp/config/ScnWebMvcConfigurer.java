package com.sleepy.mp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Configuration
public class ScnWebMvcConfigurer implements WebMvcConfigurer {

    // 配置拦截器，当前拦截器拦截除了/scn/test之外的所有/scn前缀的请求
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ScnInterceptor()).addPathPatterns("/**").excludePathPatterns("/scn/test");
    }

    private static class ScnInterceptor implements HandlerInterceptor {

        // 会在Controller处理前调用该方法，返回true则进入对应的Controller
        // 可以用来编码，安全控制，权限校验等
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            log.debug("preHandle: " + handler + ", is: " + (handler instanceof HandlerMethod));
            return true;
        }

        // 返回ModelAndView后执行，可以修改ModelAndView
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            log.debug("postHandle");
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            log.debug("afterCompletion");
        }
    }

    // 省去了controller中定义方法的流程，但是优先级低于controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
    }

    // 配置静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/file/**").addResourceLocations("classpath:/file/");
        registry.addResourceHandler("/outer/**").addResourceLocations("file:C:\\Users\\Administrator\\OneDrive\\VaadPictures\\wallpaper/");
    }

    // 配置自定义HttpMessageConverter
    // 建议用extendMessageConverters替代
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    }
}
