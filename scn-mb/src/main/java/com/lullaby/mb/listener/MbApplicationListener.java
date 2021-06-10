package com.lullaby.mb.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
public class MbApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 应用启动且还没有作任何处理
        if (event instanceof ApplicationStartingEvent) {
            System.out.println("ApplicationStarting");
            return;
        }
        // 应用启动环境准备好
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            log.info("ApplicationEnvironmentPrepared");
            return;
        }
        // ApplicationContext创建完毕
        if (event instanceof ApplicationPreparedEvent) {
            log.info("ApplicationPrepared");
            return;
        }
        // ApplicationContext refresh完毕
        if (event instanceof ApplicationStartedEvent) {
            log.info("ApplicationStarted");
            return;
        }
        // 应用启动完毕
        if (event instanceof ApplicationReadyEvent) {
            log.info("ApplicationReadyEvent");
            return;
        }
        // 应用启动失败
        if (event instanceof ApplicationFailedEvent) {
            log.info("ApplicationFailed");
            return;
        }
    }
}