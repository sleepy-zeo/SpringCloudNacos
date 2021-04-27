package com.lullaby.auth.controller;

import com.lullaby.auth.realm.UserDetailsToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class ScnController {

    @RequestMapping("/anonymous")
    @ResponseBody
    public String xxx() {
        return "anonymous";
    }

    @RequestMapping("/auth/{user}")
    @ResponseBody
    public String user(@PathVariable("user") String user) {
        return user;
    }

    @RequestMapping("/login")
    public String loginUser() {
        return "login";
    }

    @RequestMapping("/uoauth")
    @ResponseBody
    public String noAuth() {
        return "noAuth";
    }

    @RequestMapping("/api/check")
    @ResponseBody
    public String check() {
        return "check success";
    }

    @PostMapping(value = "/loginUser")
    public String login(String username, String password, Model model, HttpServletRequest request) {

        // 当前线程绑定的subject
        Subject subject = SecurityUtils.getSubject();
        // 外部传入的username+password组成一个token
        UserDetailsToken token = new UserDetailsToken(username, password);

        try {
            subject.login(token);
            log.info(request.getServletPath());

            Session session = subject.getSession(false);
            if (session != null) {
                for (Object key : session.getAttributeKeys()) {
                    /*
                    shiroSavedRequest org.apache.shiro.web.util.SavedRequest@2bfd5e9e
                    class java.lang.String

                    org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY true
                    class java.lang.String

                    org.apache.shiro.web.session.HttpServletSession.HOST_SESSION_KEY 0:0:0:0:0:0:0:1
                    class java.lang.String

                    org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY 00
                    class java.lang.String
                     */
                    System.out.println(key + " " + session.getAttribute(key));
                    System.out.println(key.getClass());
                }
                SavedRequest savedRequest = (SavedRequest) session.getAttribute("shiroSavedRequest");
                System.out.println(savedRequest.getMethod() + "-" + savedRequest.getQueryString() + "-" + savedRequest.getRequestURI() + "-" + savedRequest.getRequestUrl());
                return "forward:" + savedRequest.getRequestUrl();
            }

            return "main";
        } catch (UnknownAccountException e) {
            model.addAttribute("loginMsg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("loginMsg", "密码错误");
            return "login";
        }
    }
}
