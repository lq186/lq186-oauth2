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
    FileName: TokenController.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.controller;

import com.lq186.shiro.oauth2.auth.OAuthGrantChainContext;
import com.lq186.shiro.oauth2.service.OAuth2ClientService;
import com.lq186.shiro.oauth2.service.OAuth2UserService;
import com.lq186.shiro.oauth2.util.ResponseUtils;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TokenController {

    @Resource
    private OAuth2ClientService clientService;

    @Resource
    private OAuth2UserService userService;

    @RequestMapping(value = "/oauth/token", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity token(HttpServletRequest request) throws Exception {
        OAuthTokenRequest tokenRequest;
        try {
            tokenRequest = new OAuthTokenRequest(request);
        } catch (OAuthProblemException e) {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST, e);
        }

        return OAuthGrantChainContext.grant(tokenRequest);
    }

}
