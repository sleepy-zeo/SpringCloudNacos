package com.misty.annotation.method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class ScnAspect {

    @Pointcut(value = "@annotation(com.misty.annotation.method.ScnAnnotation)")
    private void pointcut() {
    }

    /**
     * 在方法执行前后
     *
     * @param point
     * @param scnAnnotation
     * @return
     */
    @Around(value = "pointcut() && @annotation(scnAnnotation)")
    public Object around(ProceedingJoinPoint point, ScnAnnotation scnAnnotation) {

        System.out.println("执行了around方法");

        String msg = scnAnnotation.desc();
        //拦截的类名
        Class clazz = point.getTarget().getClass();
        //拦截的方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();

        System.out.println("执行了类:" + clazz + " 方法:" + method + " 自定义消息:" + msg);

        try {
            return point.proceed(); //执行程序
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable.getMessage();
        }
    }

    /**
     * 方法执行后
     *
     * @param joinPoint
     * @param scnAnnotation
     * @param result
     * @return
     */
    @AfterReturning(value = "pointcut() && @annotation(scnAnnotation)", returning = "result")
    public Object afterReturning(JoinPoint joinPoint, ScnAnnotation scnAnnotation, Object result) {
        System.out.println("执行了afterReturning方法: result=" + result);
        return result;
    }

    /**
     * 方法执行后 并抛出异常
     *
     * @param joinPoint
     * @param scnAnnotation
     * @param ex
     */
    @AfterThrowing(value = "pointcut() && @annotation(scnAnnotation)", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, ScnAnnotation scnAnnotation, Exception ex) {
        System.out.println("执行了afterThrowing方法");
        System.out.println("请求：" + scnAnnotation.desc() + " 出现异常");
    }

}
