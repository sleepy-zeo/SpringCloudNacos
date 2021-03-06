package com.sleepy.zeo.security.access;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class ScnPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("authentication: " + authentication + ", targetDomainObject: " + targetDomainObject + ", permission: " + permission);
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        log.info("authentication: " + authentication + ", targetId: " + targetId + ", targetType: " + targetType + ", permission: " + permission);
        return true;
    }
}
