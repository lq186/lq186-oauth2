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
    FileName: OpenIdTokenEnhancer.java
    Date: 2019/3/19
    Author: lq
*/
package com.lq186.oauth2.provider;

import com.lq186.common.log.Log;
import com.lq186.common.springboot.redis.RedisUtils;
import com.lq186.common.util.RandomUtils;
import com.lq186.oauth2.consts.AccessTokenProperties;
import com.lq186.oauth2.entity.OAuth2OpenId;
import com.lq186.oauth2.service.OAuth2OpenIdService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class OpenIdTokenEnhancer implements TokenEnhancer {

    private static final Log log = Log.getLog(OpenIdTokenEnhancer.class);

    @Resource
    private OAuth2OpenIdService oAuth2OpenIdService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if ("client_credentials".equals(authentication.getOAuth2Request().getGrantType())) {
            return accessToken;
        }

        final Map<String, Object> additionalInfo = new HashMap<>(1);
        // TODO 生成或获取对应client_id的openid，并扩展至返回值中
        String clientId = authentication.getOAuth2Request().getClientId();
        UserDetails userDetails = (UserDetails) authentication.getUserAuthentication().getPrincipal();

        // 加锁执行, 如有必要, 可使用Redis进行分布式加锁
        String lockKey = new String(clientId + userDetails.getUsername()).intern();
        synchronized (lockKey) {
            OAuth2OpenId oAuth2OpenId = oAuth2OpenIdService.getOpenIdCreateNewIfNotExists(clientId, userDetails.getUsername());
            log.infof("client_id: %s, username: %s -> openid: %s", clientId, userDetails.getUsername(), oAuth2OpenId.getOpenid());
            additionalInfo.put(AccessTokenProperties.OPEN_ID, oAuth2OpenId.getOpenid());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
