package com.lullaby.error.listener;

import com.lullaby.error.event.ElEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ElEventListener {

    @Async
    @EventListener
    public void handleAddEvent(ElEvent event) {
        log.info("thread id: " + Thread.currentThread());
        log.info("Received event: {}", event);
    }
}
