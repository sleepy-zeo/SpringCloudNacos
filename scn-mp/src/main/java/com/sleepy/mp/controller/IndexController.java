package com.sleepy.mp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

@Controller
public class IndexController {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConnectionPoolDataSource connectionPoolDataSource;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
