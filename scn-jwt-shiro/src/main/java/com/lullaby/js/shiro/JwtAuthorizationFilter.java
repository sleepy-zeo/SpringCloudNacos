package com.lullaby.js.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public class JwtAuthorizationFilter extends AuthenticationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("isAccessAllowed");
        Subject subject = SecurityUtils.getSubject();
        String url = getPathWithinApplication(request);
        log.info("url: " + url);
        return subject.isPermitted(url);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.info("onAccessDenied");
        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            log.info("Not authenticated");
        } else {
            log.info("No permission");
        }
        return false;
    }
}
