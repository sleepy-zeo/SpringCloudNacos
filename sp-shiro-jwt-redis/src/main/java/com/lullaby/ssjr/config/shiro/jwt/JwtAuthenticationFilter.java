package com.lullaby.ssjr.config.shiro.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AuthenticationFilter {

    // true表示该request直接通过该filter不被拦截，false表示该request跳转到该filter的onAccessDenied方法中
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("isAccessAllowed");
        return false;
    }

    // true表示继续处理，false表示终止返回
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer")) {
            onLoginFailed(servletResponse, "jwt format not valid");
            return false;
        }
        String jwt = header.replace("Bearer", "").trim();
        if (jwt.length() == 0) {
            onLoginFailed(servletResponse, "empty jwt token");
            return false;
        }
        JwtToken jwtToken = new JwtToken(jwt);
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(jwtToken);
        } catch (Exception e) {
            onLoginFailed(servletResponse, e.getMessage());
            return false;
        }

        return true;
    }

    private void onLoginFailed(ServletResponse response, String desc) throws IOException {
        log.info("onLoginFailed, msg: " + desc);
    }
}
