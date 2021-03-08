package com.sleepy.zeo.handle;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * RequestBodyAdvice针对的是@RequestBody的请求
 */
@ControllerAdvice
public class ReqBodyAdvice implements RequestBodyAdvice {

    /**
     * 返回true，json不为null调用beforeBodyRead-afterBodyRead，为null则调用handleEmptyBody
     * @param methodParameter 调用的方法名字，比如AdviceController的user()方法
     * @param targetType json的类型，比如com.sleepy.zeo.common.User
     * @param converterType 将json转换成对应实体类的转换器类型，比如org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
     */
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 在request的body被转换和读取之前调用到
     * @param httpInputMessage 请求，可以通过inputMessage获取到请求的headers和body
     * @param methodParameter 调用的方法名字，比如AdviceController的user()方法
     * @param targetType json的类型，比如com.sleepy.zeo.common.User
     * @param converterType 将json转换成对应实体类的转换器类型，比如org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
     * @return never {@code null}
     */
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        // headers
        HttpHeaders httpHeaders = httpInputMessage.getHeaders();
        //host: localhost:2399
        //user-agent: curl/7.69.1
        //accept: */*
        //content-length: 26
        //Content-Type: application/json;charset=UTF-8
        for (String headerKey : httpHeaders.keySet()) {
            System.out.println(headerKey + ": " + httpHeaders.getFirst(headerKey));
        }

        /* inputStream是不能重复读取的，所以这里不要读，否则controller里无法获取数据
        // body
        InputStream inputStream = httpInputMessage.getBody();
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        int size;
        while ((size = inputStream.read(buffer)) > 0) {
            builder.append(new String(buffer, 0, size));
        }
        //body: {"name":"steven","age":12}
        System.out.println("body: " + builder.toString());
        */

        return httpInputMessage;
    }

    /**
     * beforeBodyRead之后调用该方法
     * @param body beforeBodyRead返回的httpInputMessage包含的body
     * @param httpInputMessage 请求，可以通过inputMessage获取到请求的headers和body
     * @param methodParameter 调用的方法名字，比如AdviceController的user()方法
     * @param targetType json的类型，比如com.sleepy.zeo.common.User
     * @param converterType 将json转换成对应实体类的转换器类型，比如org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
     * @return body，该body随后被发送至controller的对应方法中
     */
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 传入的requestBody的json为空时候进入该方法
     * @param body null
     * @param httpInputMessage 请求，可以通过inputMessage获取到请求的headers和body
     * @param methodParameter 调用的方法名字，比如AdviceController的user()方法
     * @param targetType json的类型，比如com.sleepy.zeo.common.User
     * @param converterType 将json转换成对应实体类的转换器类型，比如org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
     * @return can be {@code null}
     */
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // headers
        HttpHeaders httpHeaders = httpInputMessage.getHeaders();
        //host: localhost:2399
        //user-agent: curl/7.69.1
        //accept: */*
        //content-length: 26
        //Content-Type: application/json;charset=UTF-8
        for (String headerKey : httpHeaders.keySet()) {
            System.out.println(headerKey + ": " + httpHeaders.getFirst(headerKey));
        }
        return body;
    }
}
