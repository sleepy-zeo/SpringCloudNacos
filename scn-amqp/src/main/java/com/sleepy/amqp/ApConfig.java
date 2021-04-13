package com.sleepy.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApConfig {

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange("weshop.exchange");
    }

    @Bean
    Queue footprintQueue() {
        return new Queue("weshop.queue");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
