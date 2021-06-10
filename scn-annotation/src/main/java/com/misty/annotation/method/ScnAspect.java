package com.misty.annotation.method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class ScnAspect {

    // 方法的切面
    // @Pointcut("execution(* com.lullaby.*.OtaCheck.*(..))")
    // private void pointcut() {
    // }

    // 注解的切面
    @Pointcut(value = "@annotation(com.misty.annotation.method.ScnAnnotation)")
    private void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        // 拦截的类名
        Class clazz = point.getTarget().getClass();
        // 拦截的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        // 通过method获取引用
        ScnAnnotation annotation = method.getAnnotation(ScnAnnotation.class);

        System.out.println("执行了类:" + clazz + " 方法:" + method + " 自定义消息:" + annotation.desc());
        Object obj = point.proceed();
        System.out.println("==>" + obj);
        return point.proceed(); //执行程序
//        try {
//
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            return throwable.getMessage();
//        }
    }

}
