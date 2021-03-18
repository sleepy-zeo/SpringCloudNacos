package com.sleepy.zeo.security.exception;

import com.google.gson.Gson;
import com.sleepy.zeo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 未授权
@Slf4j
public class ScnAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());

        Result result = new Result();
        result.setCode(HttpStatus.FORBIDDEN);
        result.setDesc(HttpStatus.FORBIDDEN.getReasonPhrase());
        httpServletResponse.getWriter().write(new Gson().toJson(result));
    }
}
