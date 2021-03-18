package com.sleepy.zeo.security.jwt;

import com.google.gson.Gson;
import com.sleepy.zeo.common.AuthResult;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtManager jwtManager;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public JwtLoginFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.jwtManager = new JwtManager();
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        logger.info("username: " + username + ", password: " + password);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String username = request.getParameter("username");
        Map<String, Object> body = new HashMap<>();
        body.put("scn-username", username);
        String token = null;
        try {
            token = jwtManager.createTokenByJose4J(body);
        } catch (JoseException e) {
            logger.error(e.getMessage());
        }
        response.addHeader("Authorization", "Bearer " + token);

        AuthResult result = new AuthResult();
        result.setCode(HttpStatus.OK);
        result.setDesc(HttpStatus.OK.getReasonPhrase());
        result.setToken(token);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(new Gson().toJson(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        AuthResult result = new AuthResult();
        result.setCode(HttpStatus.UNAUTHORIZED);
        result.setDesc(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(new Gson().toJson(result));
    }
}
