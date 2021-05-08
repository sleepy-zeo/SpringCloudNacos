package com.lullaby.ssjr.process;

import com.lullaby.ssjr.common.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        ResponseResult responseResult = new ResponseResult(400, "failed", e.getMessage());
        return responseResult;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseResult runtimeException(RuntimeException e) {
        ResponseResult responseResult = new ResponseResult(400, "failed", e.getMessage());
        return responseResult;
    }
}
