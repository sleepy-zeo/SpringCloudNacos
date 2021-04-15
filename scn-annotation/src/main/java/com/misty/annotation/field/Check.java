package com.misty.annotation.field;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ParamValidator.class)
public @interface Check {

    String[] paramValues();

    String message() default "Check error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
