package com.sleepy.zeo.handle;

import com.sleepy.zeo.common.SCNResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ResponseBodyAdvice针对的是@ResponseBody的请求
 */
@ControllerAdvice
public class ResBodyAdvice implements ResponseBodyAdvice<Object> {

    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 在controller的方法返回body信息后传输到这里，然后由这里传输到client的显示界面
     * @param body 返回的对象，例如"hello world"，User{name='null', age=0}
     * @param methodParameter 调用方法的名称，例如AdviceController的re()方法
     * @param selectedContentType 返回的ContentType，比如text/plain
     * @param selectedConverterType 选中的消息转换器的类型，比如org.springframework.http.converter.StringHttpMessageConverter
     * @param request request
     * @param response response
     * @return 返回到请求客户端的body信息
     */
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        System.out.println("ResBodyAdvice beforeBodyWrite, body: " + body + ", returnType: "
                + methodParameter + ", selectedContentType:" + selectedContentType + ", selectedConverterType: " + selectedConverterType);

        if (body instanceof SCNResult) {
            System.out.println("xxxx");
            return body;
        }

        System.out.println("yyyy");

        return new SCNResult(200, "success", body);
    }
}
