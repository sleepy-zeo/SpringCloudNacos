package com.sleepy.zeo.exception;

import com.sleepy.zeo.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result defaultExceptionHandler(Exception e) {
        Result result = new Result();
        result.setCode(HttpStatus.BAD_REQUEST);
        result.setDesc(e.getMessage());
        result.setData(e.getMessage());
        return result;
    }
}
