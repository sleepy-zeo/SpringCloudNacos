package com.sleepy.amqp.consumer;

import com.sleepy.amqp.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@RabbitListener(queues = "weshop.queue")
public class ApConsumer {

    @RabbitHandler
    public void processMessage(@Payload User body, @Headers Map<String, Object> headers) {
        System.out.println("body2：" + body);
        System.out.println("Headers2：" + headers);
    }

    @RabbitHandler
    public void processMessage(@Payload String body, @Headers Map<String, Object> headers) {
        System.out.println("body4：" + body);
        System.out.println("Headers4：" + headers);
    }

    @RabbitHandler
    public void processMessage(@Payload byte[] body, @Headers Map<String, Object> headers) {
        System.out.println("body6：" + Arrays.toString(body));
        System.out.println("Headers6：" + headers);
    }
}
