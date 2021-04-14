package com.misty.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited()
public @interface LogAnnotation {

    String desc();
}
