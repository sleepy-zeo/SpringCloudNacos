package com.lullaby.error.event;

import org.springframework.context.ApplicationEvent;

public class ElEvent extends ApplicationEvent {

    private Object data;

    public ElEvent(Object source, Object data) {
        super(source);
        this.data = data;
    }

    @Override
    public String toString() {
        return "ElEvent{" +
                "data=" + data +
                ", source=" + source +
                '}';
    }
}
