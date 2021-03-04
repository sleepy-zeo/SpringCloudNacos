package com.sleepy.zeo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${createdBy:null}")
    private String createdBy;

    @Value("${createdOn:null}")
    private String createdOn;

    @Value("${currentEnv:null}")
    private String currentEnv;

    @RequestMapping("/createdBy")
    @ResponseBody
    public String createdBy() {
        return createdBy;
    }

    @RequestMapping("/createdOn")
    @ResponseBody
    public String createdOn() {
        return createdOn;
    }

    @RequestMapping("/env")
    @ResponseBody
    public String currentEnv() {
        return currentEnv;
    }
}
