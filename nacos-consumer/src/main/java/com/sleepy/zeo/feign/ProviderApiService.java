package com.sleepy.zeo.feign;

import com.sleepy.zeo.feign.api.ProviderApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "nacos-provider", fallbackFactory = ProviderApiFallbackService.class)
public interface ProviderApiService extends ProviderApi {
}
