package com.misty.annotation.field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.util.Arrays;
import java.util.List;

public class ParamValidator implements ConstraintValidator<Check, Object> {

    private List<String> paramValues;
    private String message;
    private List<Class<?>> groups;
    private List<Class<? extends Payload>> payloads;

    @Override
    public void initialize(Check constraintAnnotation) {
        paramValues = Arrays.asList(constraintAnnotation.paramValues());
        message = constraintAnnotation.message();
        groups = Arrays.asList(constraintAnnotation.groups());
        payloads = Arrays.asList(constraintAnnotation.payload());

        System.out.println("paramValues: " + paramValues);
        System.out.println("message: " + message);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return paramValues.contains(o);
    }
}
