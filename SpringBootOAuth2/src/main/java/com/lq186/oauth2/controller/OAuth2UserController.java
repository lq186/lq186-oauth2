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
    FileName: OAuth2UserController.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.controller;

import com.lq186.oauth2.consts.AccessTokenProperties;
import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.user.SimpleUserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OAuth2UserController {

    @Resource
    private ResourceServerTokenServices resourceServerTokenServices;

    @RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> user(Principal principal) {
        Map<String, Object> resultMap = new HashMap<>();
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
            Object objectPrincipal = oAuth2Authentication.getPrincipal();
            if (objectPrincipal instanceof UserDetails) {
                SimpleUserDetailsImpl userDetails = new SimpleUserDetailsImpl();
                BeanUtils.copyProperties(objectPrincipal, userDetails);
                resultMap.clear();
                resultMap.put("username", userDetails.getUsername());
                resultMap.put("state", userDetails.getState());
                resultMap.put("show_name", userDetails.getShowName());
                resultMap.put("head_picture", userDetails.getHeadPicture());
                resultMap.put("created_time", userDetails.getCreatedTime());
                resultMap.put("login_ip", userDetails.getLoginIp());
                resultMap.put("login_time", userDetails.getLoginTime());
                // 添加openid支持
                Object objectAuthenticationDetails = oAuth2Authentication.getDetails();
                if (objectAuthenticationDetails instanceof OAuth2AuthenticationDetails) {
                    OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) objectAuthenticationDetails;
                    String tokenValue = authenticationDetails.getTokenValue();
                    OAuth2AccessToken accessToken = resourceServerTokenServices.readAccessToken(tokenValue);
                    if (accessToken instanceof DefaultOAuth2AccessToken) {
                        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
                        Map<String, Object> additionalMap = defaultOAuth2AccessToken.getAdditionalInformation();
                        if (null != additionalMap && additionalMap.containsKey(AccessTokenProperties.OPEN_ID)) {
                            resultMap.put(AccessTokenProperties.OPEN_ID, additionalMap.get(AccessTokenProperties.OPEN_ID));
                        }
                    }
                }
                return resultMap;
            } else if (objectPrincipal instanceof String) {
                resultMap.clear();
                // TODO 添加客户端信息?
                resultMap.put("client_id", String.valueOf(objectPrincipal));
                return resultMap;
            }
        }

        resultMap.clear();
        resultMap.put("error", "Bad Principal");
        resultMap.put("error_description", "错误的凭证类型");
        return resultMap;
    }

}
