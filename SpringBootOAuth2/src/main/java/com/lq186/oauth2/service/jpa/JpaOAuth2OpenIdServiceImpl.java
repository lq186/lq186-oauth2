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
    FileName: JpaOAuth2OpenIdServiceImpl.java
    Date: 2019/3/19
    Author: lq
*/
package com.lq186.oauth2.service.jpa;

import com.lq186.common.util.RandomUtils;
import com.lq186.oauth2.entity.OAuth2Client;
import com.lq186.oauth2.entity.OAuth2OpenId;
import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.repo.OAuth2ClientRepo;
import com.lq186.oauth2.repo.OAuth2OpenIdRepo;
import com.lq186.oauth2.repo.OAuth2UserRepo;
import com.lq186.oauth2.service.OAuth2OpenIdService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class JpaOAuth2OpenIdServiceImpl implements OAuth2OpenIdService {

    @Resource
    private OAuth2OpenIdRepo openIdRepo;

    @Resource
    private OAuth2ClientRepo clientRepo;

    @Resource
    private OAuth2UserRepo userRepo;

    @Transactional
    @Override
    public OAuth2OpenId getOpenIdCreateNewIfNotExists(String clientId, String username) {

        Optional<OAuth2Client> clientOptional = clientRepo.getByClientId(clientId);
        if (!clientOptional.isPresent()) {
            throw new ClientRegistrationException(String.format("客户端[%s]不存在", clientId));
        }

        Optional<OAuth2User> userOptional = userRepo.getByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(String.format("用户名[%s]不存在", username));
        }

        String clientBzid = clientOptional.get().getBzid();
        String userBzid = userOptional.get().getBzid();

        Optional<OAuth2OpenId> openIdOptional = openIdRepo.getByClientBzidAndUserBzid(clientBzid, userBzid);
        if (openIdOptional.isPresent()) {
            return openIdOptional.get();
        }

        String openid = DigestUtils.md5Hex(clientBzid + userBzid);
        OAuth2OpenId oAuth2OpenId = new OAuth2OpenId();
        oAuth2OpenId.setBzid(RandomUtils.randomUUID());
        oAuth2OpenId.setClientBzid(clientBzid);
        oAuth2OpenId.setUserBzid(userBzid);
        oAuth2OpenId.setOpenid(openid);
        oAuth2OpenId.setCreatedTime(System.currentTimeMillis());

        return openIdRepo.saveAndFlush(oAuth2OpenId);
    }
}
