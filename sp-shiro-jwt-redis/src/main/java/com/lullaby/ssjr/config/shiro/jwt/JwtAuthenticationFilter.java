package com.lullaby.ssjr.config.shiro.jwt;

import com.lullaby.ssjr.common.Constants;
import com.lullaby.ssjr.common.CustomException;
import com.lullaby.ssjr.config.redis.JedisTemplate;
import com.lullaby.ssjr.utils.JwtUtil;
import com.lullaby.ssjr.utils.PropertiesUtil;
import com.lullaby.ssjr.utils.UUIDUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends AuthenticationFilter {

    private final JedisTemplate jedisTemplate;

    public JwtAuthenticationFilter(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

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
        // 前端传过来的token
        String jwt = header.replace("Bearer", "").trim();
        if (jwt.length() == 0) {
            onLoginFailed(servletResponse, "empty jwt token");
            return false;
        }
        JwtToken jwtToken = new JwtToken(jwt);
        return authenticateToken(servletRequest, servletResponse, jwtToken);
    }

    private void onLoginFailed(ServletResponse response, String desc) throws IOException {
        log.info("onLoginFailed, msg: " + desc);
    }

    private boolean authenticateToken(ServletRequest servletRequest, ServletResponse servletResponse, JwtToken jwtToken) throws IOException {

        String jwt = (String) jwtToken.getCredentials();

        Claims claims;
        claims = JwtUtil.getClaimByToken(jwt);
        if (claims == null) {
            throw new UnknownAccountException("claims is null");
        }
        String accessKey = (String) claims.get(Constants.JWT_CLAIMS_KEY_ACCESS_KEY);
        log.info("authenticateToken, accessKey: " + accessKey);

        if (jedisTemplate.exists(Constants.PREFIX_SHIRO_CACHE + accessKey)) {
            log.info("cache exists");
            // redis中存在缓存的认证信息
            return true;
        } else if (jedisTemplate.exists(Constants.PREFIX_SHIRO_REFRESH_TOKEN + accessKey)) {
            log.info("refresh exists");
            // redis中不存在缓存的认证信息，但是存在refreshToken信息

            String account = accessKey.substring(0, accessKey.length() - UUIDUtil.uuidLength());
            String uuid = UUIDUtil.uuid();
            if (jedisTemplate.exists(Constants.PREFIX_SHIRO_REFRESH_TOKEN + accessKey)) {
                jedisTemplate.delKey(Constants.PREFIX_SHIRO_REFRESH_TOKEN + accessKey);
            }
            return refreshToken(servletResponse, account, uuid);
        } else {
            log.info("login to authenticate whether db exists");
            // redis中没有任何相关信息
            Subject subject = SecurityUtils.getSubject();
            subject.login(jwtToken);
            log.info("db exists");
            String account = accessKey.substring(0, accessKey.length() - UUIDUtil.uuidLength());
            String uuid = UUIDUtil.uuid();
            return refreshToken(servletResponse, account, uuid);
        }
    }

    private boolean refreshToken(ServletResponse servletResponse, String account, String uuid) {
        String refreshedAccessKey = account + uuid;
        try {
            PropertiesUtil.readProperties("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }
        String cacheTokenExpireTime = PropertiesUtil.getProperty("shiro.cacheTokenExpireTime");
        String refreshTokenExpireTime = PropertiesUtil.getProperty("shiro.refreshTokenExpireTime");
        jedisTemplate.setObject(Constants.PREFIX_SHIRO_CACHE + refreshedAccessKey, refreshedAccessKey, Integer.parseInt(cacheTokenExpireTime));
        jedisTemplate.setObject(Constants.PREFIX_SHIRO_REFRESH_TOKEN + refreshedAccessKey, refreshedAccessKey, Integer.parseInt(refreshTokenExpireTime));

        String token = JwtUtil.generateToken(refreshedAccessKey);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(servletResponse);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

        return true;
    }
}
