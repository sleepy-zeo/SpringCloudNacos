package com.misty.annotation.type;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PermissionCheckInterceptor extends HandlerInterceptorAdapter {

    /**
     * 处理器处理之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        PermissionCheck permission = findPermissionCheck(handlerMethod);

        if (permission == null) {
            return true;
        }

        String resourceKey = permission.resourceKey();

        //TODO: 权限校验一般需要获取用户信息，通过查询数据库进行权限校验
        if ("testKey".equals(resourceKey)) {
            return true;
        }

        // 这里校验失败直接抛出Exception
        throw new Exception("permissionCheck failed");
    }

    /**
     * 根据handlerMethod返回注解信息
     *
     * @param handlerMethod 方法对象
     * @return PermissionCheck注解
     */
    private PermissionCheck findPermissionCheck(HandlerMethod handlerMethod) {
        //在方法上寻找注解
        PermissionCheck permission = handlerMethod.getMethodAnnotation(PermissionCheck.class);
        if (permission == null) {
            //在类上寻找注解
            permission = handlerMethod.getBeanType().getAnnotation(PermissionCheck.class);
        }

        return permission;
    }
}

