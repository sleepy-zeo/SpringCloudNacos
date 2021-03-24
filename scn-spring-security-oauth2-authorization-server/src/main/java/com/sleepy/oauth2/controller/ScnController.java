package com.sleepy.oauth2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScnController {

    @RequestMapping("")
    public String index() {
        return "hello scn";
    }
}
