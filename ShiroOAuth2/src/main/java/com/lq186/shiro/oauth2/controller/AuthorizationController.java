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
import com.lq186.shiro.oauth2.consts.*;
import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import com.lq186.shiro.oauth2.service.*;
import com.lq186.shiro.oauth2.util.ResponseUtils;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AuthorizationController {

    @Resource
    private OAuth2ClientService clientService;

    @Resource
    private AuthorizationCodeService authorizationCodeService;

    @Resource
    private OAuth2TokenService tokenService;

    @RequestMapping(value = {"/oauth/authorize"})
    public Object authorize(HttpServletRequest request) throws OAuthSystemException, URISyntaxException {

        String userOAuthApproval = request.getParameter(ParameterNames.USER_OAUTH_APPROVAL);
        Subject subject = SecurityUtils.getSubject();

        if (StringUtils.isNotBlank(userOAuthApproval) && Boolean.parseBoolean(userOAuthApproval)) {
            return doConfirmGrant(request, subject);
        } else {
            OAuthAuthzRequest authzRequest;
            try {
                authzRequest = new OAuthAuthzRequest(request);
            } catch (OAuthProblemException e) {
                return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST, e);
            }
            return doGrant(request, subject, authzRequest);
        }
    }

    private ResponseEntity tokenResponse(HttpServletRequest request, OAuth2Client client, Map<String, String> parameterMap) throws OAuthSystemException, URISyntaxException {
        // 加载或生成Token
        OAuthToken token;
        try {
            token = tokenService.loadOrCreateToken(client, parameterMap.get(OAuth.OAUTH_SCOPE));
        } catch (OAuthProblemException e) {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST, e);
        }

        // 生成授权码
        String authorizationCode = authorizationCodeService.createAuthorizationCode(client.getClientId(), token);

        // 重定向到用户指定的路径
        OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
        builder.setCode(authorizationCode);

        builder.setParam(OAuth.OAUTH_STATE, parameterMap.get(OAuth.OAUTH_STATE));

        final OAuthResponse response = builder.location(parameterMap.get(OAuth.OAUTH_REDIRECT_URI)).buildQueryMessage();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(response.getLocationUri()));
        return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
    }

    private Object doGrant(HttpServletRequest request, Subject subject, OAuthAuthzRequest authzRequest) throws OAuthSystemException, URISyntaxException {

        if (StringUtils.isBlank(authzRequest.getClientId())) {
            return ResponseUtils.errorClientIdResponse();
        }

        Optional<OAuth2Client> clientOptional = clientService.getByClientId(authzRequest.getClientId());
        if (!clientOptional.isPresent()) {
            return ResponseUtils.errorClientIdResponse();
        }

        // 如果没有登录，则去登录
        if (!subject.isAuthenticated()) {
            subject.getSession(true).setAttribute(SessionAttributes.AUTHORIZATION_REDIRECT_URI, request.getRequestURI() + "?" + request.getQueryString());
            return "login";
        }

        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(OAuth.OAUTH_SCOPE, authzRequest.getScopes().stream().collect(Collectors.joining(" ")));
        parameterMap.put(OAuth.OAUTH_REDIRECT_URI, authzRequest.getRedirectURI());
        parameterMap.put(OAuth.OAUTH_STATE, authzRequest.getState());
        parameterMap.put(OAuth.OAUTH_RESPONSE_TYPE, authzRequest.getResponseType());
        parameterMap.put(OAuth.OAUTH_CLIENT_ID, authzRequest.getClientId());

        Object isGrant = subject.getSession(true).getAttribute(SessionAttributes.AUTHORIZATION_CONFIRM_ACCESS);
        if (null == isGrant || !(isGrant instanceof Boolean) || !(Boolean) isGrant) {
            subject.getSession(true).setAttribute(SessionAttributes.AUTHORIZATION_REQUEST_PARAM_MAP, parameterMap);
            subject.getSession(true).setAttribute(SessionAttributes.AUTHORIZATION_REQUEST_CLINET_INFO, clientOptional.get());
            request.setAttribute(RequestAttributes.SCOPES, authzRequest.getScopes());
            request.setAttribute(RequestAttributes.CLIENT, clientOptional.get());
            return "grant";
        }

        return tokenResponse(request, clientOptional.get(), parameterMap);
    }

    private ResponseEntity doConfirmGrant(HttpServletRequest request, Subject subject) throws OAuthSystemException, URISyntaxException {
        subject.getSession(true).setAttribute(SessionAttributes.AUTHORIZATION_CONFIRM_ACCESS, true);
        Object objectParamMap = subject.getSession(true).getAttribute(SessionAttributes.AUTHORIZATION_REQUEST_PARAM_MAP);
        Object clientObject = subject.getSession(true).getAttribute(SessionAttributes.AUTHORIZATION_REQUEST_CLINET_INFO);
        if (null != objectParamMap && objectParamMap instanceof Map && null != clientObject && clientObject instanceof OAuth2Client) {
            return tokenResponse(request, (OAuth2Client) clientObject, (Map<String, String>) objectParamMap);
        } else {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST, OAuthError.CodeResponse.INVALID_REQUEST, ErrorDescriptions.UNKNOWN_ERROR);
        }
    }

}
