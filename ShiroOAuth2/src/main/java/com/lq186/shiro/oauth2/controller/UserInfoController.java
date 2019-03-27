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
    FileName: UserInfoController.java
    Date: 2019/3/27
    Author: lq
*/
package com.lq186.shiro.oauth2.controller;

import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import com.lq186.shiro.oauth2.enitty.OAuth2User;
import com.lq186.shiro.oauth2.service.OAuth2TokenService;
import com.lq186.shiro.oauth2.util.ResponseUtils;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class UserInfoController {

    @Resource
    private OAuth2TokenService tokenService;

    @RequestMapping(value = {"/openapi/user-info"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity userInfo(HttpServletRequest request) throws OAuthSystemException {

        OAuthAccessResourceRequest resourceRequest;
        try {
            resourceRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        } catch (OAuthProblemException e) {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST, e);
        }

        final String accessToken = resourceRequest.getAccessToken();
        Optional<OAuthToken> oAuthToken = tokenService.readAccessToken(accessToken);
        if (!oAuthToken.isPresent()) {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_UNAUTHORIZED, OAuthError.ResourceResponse.INVALID_TOKEN, ErrorDescriptions.INVALID_TOKEN);
        }

        Optional<OAuth2User> userOptional = tokenService.getOAuth2UserByAccessToken(accessToken);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(userOptional.get());
        } else {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_UNAUTHORIZED, OAuthError.ResourceResponse.INVALID_TOKEN, ErrorDescriptions.INVALID_TOKEN);
        }
    }

}
