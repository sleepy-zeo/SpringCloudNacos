package com.lullaby.mb.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1) // 如果有多个runner，需要配置各个runner的顺序
public class MbApplicationRunner implements ApplicationRunner {

    // 系统启动完毕后会调用，等价于ApplicationListener中收到ApplicationReadyEvent事件
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationStarted");
    }
}
