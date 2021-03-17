package com.sleepy.zeo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @RequestMapping("/info")
    public String info() {
        return "info";
    }
}
