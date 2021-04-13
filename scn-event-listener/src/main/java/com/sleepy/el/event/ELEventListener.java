package com.sleepy.el.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ELEventListener {
    private static final Logger log = LoggerFactory.getLogger(ELEventListener.class);

    @Async
    @EventListener
    public void handleAddEvent(ELEvent event) {
        log.info("发布的data为: {}  ", event);
        log.info("当前线程为: {}  {}",Thread.currentThread().getName(),Thread.currentThread().getId());
    }
}
