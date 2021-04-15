package com.lullaby.prop.controller;

import com.lullaby.prop.config.WxProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScnController {

    @Autowired
    private WxProperties properties;

    @RequestMapping("/test")
    public String test() {
        return properties.getAppId() + "#" + properties.getSecret() + "#" + properties.getToken();
    }
}
