package com.lullaby.condition.sub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ScnController {

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/test")
    @ResponseBody
    public Map<String, Computer> test() {
        Map<String, Computer> map = applicationContext.getBeansOfType(Computer.class);
        return map;
    }
}
