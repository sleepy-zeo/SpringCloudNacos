package com.sleepy.zeo.feign.api;

import com.sleepy.zeo.data.FeignResult;
import org.springframework.web.bind.annotation.RequestMapping;

public interface ProviderApi {

    @RequestMapping("/info")
    FeignResult info();
}
