package com.misty.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// FIELD通过validator
// METHOD通过aop
// TYPE通过interceptor
@SpringBootApplication(scanBasePackages = "com.misty")
public class AnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationApplication.class, args);
    }
}
