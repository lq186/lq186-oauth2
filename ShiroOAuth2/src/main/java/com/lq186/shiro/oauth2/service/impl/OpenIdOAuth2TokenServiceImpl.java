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
    FileName: OpenIdOAuth2TokenServiceImpl.java
    Date: 2019/3/26
    Author: lq
*/
package com.lq186.shiro.oauth2.service.impl;

import com.lq186.shiro.oauth2.bean.AdditionalInfoOAuthToken;
import com.lq186.shiro.oauth2.consts.AccessTokenProperties;
import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import com.lq186.shiro.oauth2.enitty.OAuth2OpenId;
import com.lq186.shiro.oauth2.enitty.OAuth2User;
import com.lq186.shiro.oauth2.service.OAuth2OpenIdService;
import com.lq186.shiro.oauth2.service.OAuth2TokenService;
import com.lq186.shiro.oauth2.service.OAuth2TokenStoreService;
import com.lq186.shiro.oauth2.service.OAuth2UserService;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class OpenIdOAuth2TokenServiceImpl implements OAuth2TokenService {

    @Resource
    private OAuth2OpenIdService openIdService;

    @Resource
    private OAuth2UserService userService;

    @Resource
    private OAuthIssuer oAuthIssuer;

    @Resource
    private OAuth2TokenStoreService tokenStoreService;

    @Override
    public OAuthToken loadOrCreateToken(OAuth2Client client, String scopes) throws OAuthSystemException, OAuthProblemException {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String username;
        if (principal instanceof String) {
            username = (String) principal;
        } else if (principal instanceof SimpleAuthenticationInfo) {
            username = (String) ((SimpleAuthenticationInfo) principal).getPrincipals().getPrimaryPrincipal();
        } else {
            throw OAuthProblemException.error(OAuthError.CodeResponse.ACCESS_DENIED, ErrorDescriptions.ACCESS_DENIED);
        }

        Optional<OAuth2User> userOptional = userService.getByUsername(username);
        if (!userOptional.isPresent()) {
            throw OAuthProblemException.error(OAuthError.CodeResponse.ACCESS_DENIED, ErrorDescriptions.ACCESS_DENIED);
        }

        AdditionalInfoOAuthToken token = new AdditionalInfoOAuthToken(oAuthIssuer.accessToken(),
                AccessTokenProperties.TOKEN_TYPE_VALUE,
                AccessTokenProperties.EXPIRES_IN_VALUE,
                oAuthIssuer.refreshToken(), scopes);
        OAuth2OpenId oAuth2OpenId = openIdService.getOpenIdCreateNewIfNotExists(client, userOptional.get());
        token.addAdditionalInfo("openid", oAuth2OpenId.getOpenid());
        tokenStoreService.saveToken(token, client, userOptional.get());
        return token;
    }

    @Override
    public Optional<OAuthToken> readAccessToken(String accessToken) {
        return tokenStoreService.getByAccessToken(accessToken);
    }

    @Override
    public Optional<OAuth2User> getOAuth2UserByAccessToken(String accessToken) {
        return tokenStoreService.getUsernameByAccessToken(accessToken);
    }
}
