package com.sleepy.zeo.controller;

import com.sleepy.zeo.data.FeignResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProviderController {

    @RequestMapping("/info")
    @ResponseBody
    FeignResult info() {
        return new FeignResult(200, "success", "Hi, this is nacos provider", null);
    }
}
