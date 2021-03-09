package com.sleepy.zeo.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView defaultExceptionHandler(Exception e) {
        System.out.println("Exception");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", e.toString());
        modelAndView.setViewName("error_exception");
        return modelAndView;
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public String npExceptionHandler(NullPointerException e) {
        System.out.println("NullPointerException");
        return "er";
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    public String rtExceptionHandler(RuntimeException e) {
        System.out.println("RuntimeException");
        return e.getMessage();
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    @ResponseBody
    public NumberFormatException nfExceptionHandler(NumberFormatException e) {
        System.out.println("NumberFormatException");
        return e;
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ModelAndView nhExceptionHandler(NoHandlerFoundException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("path",e.getRequestURL());
        modelAndView.setViewName("error_404");
        return modelAndView;
    }

}
