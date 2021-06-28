package com.lullaby.error.error;

import com.lullaby.error.event.ElEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    @ResponseBody
    public String error404() {
        return HttpStatus.NOT_FOUND.getReasonPhrase();
    }

    @RequestMapping("/405")
    @ResponseBody
    public String error405() {
        return HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase();
    }

    @RequestMapping("/500")
    @ResponseBody
    public String error500() {
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    @RequestMapping("/exp_illegal_argument")
    @ResponseBody
    public String expIllegalArgument() {
        return "IllegalArgument";
    }

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/send")
    @ResponseBody
    public String send(){
        applicationContext.publishEvent(new ElEvent("el","1111"));
        log.info("thread id: "+Thread.currentThread());
        return "success";
    }
}
