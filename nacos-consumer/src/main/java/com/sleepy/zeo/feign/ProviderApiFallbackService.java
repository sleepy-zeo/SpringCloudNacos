package com.sleepy.zeo.feign;

import com.sleepy.zeo.data.FeignResult;
import com.sleepy.zeo.feign.api.ProviderApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProviderApiFallbackService implements FallbackFactory<ProviderApi> {

    public ProviderApi create(final Throwable throwable) {

        return new ProviderApi() {

            public FeignResult info() {
                return new FeignResult(500, "server error", null, throwable);
            }
        };
    }
}
