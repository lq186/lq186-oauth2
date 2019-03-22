/*    
    Copyright ©2019 lq186.com 
 
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
 
        http://www.apache.org/licenses/LICENSE-2.0
 
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
/*
    FileName: AuthorizationController.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.controller;

import com.lq186.common.util.StringUtils;
import com.lq186.shiro.oauth2.bean.AdditionalInfoOAuthToken;
import com.lq186.shiro.oauth2.consts.AccessTokenProperties;
import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import com.lq186.shiro.oauth2.consts.ParameterNames;
import com.lq186.shiro.oauth2.consts.RequestAttributes;
import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import com.lq186.shiro.oauth2.service.AuthorizationCodeService;
import com.lq186.shiro.oauth2.service.OAuth2ClientService;
import com.lq186.shiro.oauth2.util.ResponseUtils;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Controller
public class AuthorizationController {

    @Resource
    private OAuth2ClientService clientService;

    @Resource
    private AuthorizationCodeService authorizationCodeService;

    @Resource
    private OAuthIssuer oAuthIssuer;

    @RequestMapping(value = {"/oauth/authorize"})
    public Object authorize(HttpServletRequest request) throws OAuthSystemException, URISyntaxException {

        OAuthTokenRequest tokenRequest;
        try {
            tokenRequest = new OAuthTokenRequest(request);
        } catch (OAuthProblemException e) {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST, e);
        }

        if (StringUtils.isBlank(tokenRequest.getClientId())) {
            return ResponseUtils.errorClientIdResponse();
        }

        Optional<OAuth2Client> clientOptional = clientService.getByClientId(tokenRequest.getClientId());
        if (!clientOptional.isPresent()) {
            return ResponseUtils.errorClientIdResponse();
        }

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            if (!login(subject, request)) {
                return "login";
            }
        }

        AdditionalInfoOAuthToken token = new AdditionalInfoOAuthToken(oAuthIssuer.accessToken(),
                AccessTokenProperties.TOKEN_TYPE_VALUE,
                AccessTokenProperties.EXPIRES_IN_VALUE,
                oAuthIssuer.refreshToken(), tokenRequest.getParam(OAuth.OAUTH_SCOPE));
        String authorizationCode = authorizationCodeService.createAuthorizationCode(token);

        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
        builder.setCode(authorizationCode);

        String redirectURI = tokenRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
        final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(response.getLocationUri()));
        return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));

    }

    private boolean login(final Subject subject, final HttpServletRequest request) {
        if (HttpMethod.GET.matches(request.getMethod())) {
            request.setAttribute(RequestAttributes.ERROR, ErrorDescriptions.UNSUPPORTED_HTTP_METHOD);
            return false;
        }

        final String username = request.getParameter(ParameterNames.USERNAME);
        final String password = request.getParameter(ParameterNames.PASSWORD);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            request.setAttribute(RequestAttributes.ERROR, ErrorDescriptions.INVALID_USERNAME_OR_PASSWORD);
            return false;
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        try {
            subject.login(usernamePasswordToken);
            return true;
        } catch (AuthenticationException e) {
            request.setAttribute(RequestAttributes.ERROR, ErrorDescriptions.INVALID_USERNAME_OR_PASSWORD);
            return false;
        }
    }

}
