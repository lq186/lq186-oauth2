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
    FileName: InMemoryOAuth2TokenStoreServiceImpl.java
    Date: 2019/3/27
    Author: lq
*/
package com.lq186.shiro.oauth2.service.impl;

import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import com.lq186.shiro.oauth2.enitty.OAuth2User;
import com.lq186.shiro.oauth2.service.OAuth2TokenStoreService;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InMemoryOAuth2TokenStoreServiceImpl implements OAuth2TokenStoreService {

    private static final Map<String, OAuthToken> TOKEN_MAP = new HashMap<>();

    private static final Map<String, OAuth2User> TOKEN_USER_MAP = new HashMap<>();

    private static final Map<String, String> TOKEN_CLIENT_ID_MAP = new HashMap<>();

    @Override
    public void saveToken(OAuthToken token, OAuth2Client client, OAuth2User oAuth2User) {
        TOKEN_MAP.put(token.getAccessToken(), token);
        TOKEN_USER_MAP.put(token.getAccessToken(), oAuth2User);
        TOKEN_CLIENT_ID_MAP.put(token.getAccessToken(), client.getClientId());
    }

    @Override
    public Optional<OAuthToken> getByAccessToken(String accessToken) {
        return Optional.ofNullable(TOKEN_MAP.get(accessToken));
    }

    @Override
    public Optional<OAuth2User> getUsernameByAccessToken(String accessToken) {
        return Optional.ofNullable(TOKEN_USER_MAP.get(accessToken));
    }
}
