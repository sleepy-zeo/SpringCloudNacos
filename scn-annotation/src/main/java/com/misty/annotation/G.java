package com.misty.annotation;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class G {

    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    public String nfExceptionHandler(BindException e) {
        System.out.println("BindException: " + e.getMessage());
        return "BindException";
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public String nfExceptionHandler(Exception e) {
        System.out.println("Ex: " + e.getMessage());
        return "Ex";
    }
}
