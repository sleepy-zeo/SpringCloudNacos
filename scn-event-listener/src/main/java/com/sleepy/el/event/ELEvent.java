package com.sleepy.el.event;

import org.springframework.context.ApplicationEvent;

public class ELEvent extends ApplicationEvent {

    private Object data;

    public ELEvent(Object source, Object data) {
        super(source);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ELEvent[" +
                "data=" + data +
                ", source=" + source +
                ']';
    }
}
