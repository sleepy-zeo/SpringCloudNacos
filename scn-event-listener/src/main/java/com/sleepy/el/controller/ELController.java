package com.sleepy.el.controller;

import com.sleepy.el.event.ELEvent;
import com.sleepy.el.event.ELEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ELController {
    private static final Logger log = LoggerFactory.getLogger(ELEventListener.class);

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/el")
    @ResponseBody
    public String el(){
        log.info("send");
        applicationContext.publishEvent(new ELEvent("el","1057"));
        return "success";
    }
}
