package com.sleepy.zeo.controller;

import com.sleepy.zeo.data.FeignResult;
import com.sleepy.zeo.feign.ProviderApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ConsumerController {

    private ProviderApiService providerApiService;

    @Autowired
    public void setProviderApiService(ProviderApiService providerApiService) {
        this.providerApiService = providerApiService;
    }

    @RequestMapping("/pv")
    @ResponseBody
    FeignResult pv(){
        return providerApiService.info();
    }
}
