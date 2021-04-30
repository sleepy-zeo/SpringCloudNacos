package com.lullaby.js.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsController {

    @RequestMapping("/api/js")
    public String js() {
        return "js";
    }
}
