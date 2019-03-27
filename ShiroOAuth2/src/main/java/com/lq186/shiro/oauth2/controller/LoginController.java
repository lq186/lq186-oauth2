package com.lq186.shiro.oauth2.controller;

import com.lq186.common.util.StringUtils;
import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import com.lq186.shiro.oauth2.consts.ParameterNames;
import com.lq186.shiro.oauth2.consts.RequestAttributes;
import com.lq186.shiro.oauth2.consts.SessionAttributes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final String LOGIN_PAGE = "login";

    @GetMapping(value = {"/login"})
    public String loginPage(HttpServletRequest request) {
        return LOGIN_PAGE;
    }

    @PostMapping(value = {"/login"})
    public String login(HttpServletRequest request) {

        final String username = request.getParameter(ParameterNames.USERNAME);
        final String password = request.getParameter(ParameterNames.PASSWORD);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            request.setAttribute(RequestAttributes.ERROR, ErrorDescriptions.INVALID_USERNAME_OR_PASSWORD);
            return LOGIN_PAGE;
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            request.setAttribute(RequestAttributes.ERROR, ErrorDescriptions.INVALID_USERNAME_OR_PASSWORD);
            return LOGIN_PAGE;
        }

        String forwardUri = (String) SecurityUtils.getSubject().getSession(true).getAttribute(SessionAttributes.AUTHORIZATION_REDIRECT_URI);
        if (StringUtils.isBlank(forwardUri)) {
            forwardUri = "/";
        }
        System.out.println(forwardUri);
        return "forward:" + forwardUri;
    }

}
