package com.lullaby.error.error;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ScnErrorPageRegistrar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        // 优先依据异常显示错误页面
        registry.addErrorPages(new ErrorPage(IllegalArgumentException.class,"/error/exp_illegal_argument"));

        // 如果没有匹配的异常，再依据错误码显示错误页面
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
        registry.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405"));
        registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));

    }
}
