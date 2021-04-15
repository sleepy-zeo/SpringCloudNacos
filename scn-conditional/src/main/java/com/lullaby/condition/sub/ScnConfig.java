package com.lullaby.condition.sub;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingClass("com.lullaby.condition.sub.BeanConfig")
public class ScnConfig {

    public ScnConfig() {
        System.out.println("cons");
    }
}
