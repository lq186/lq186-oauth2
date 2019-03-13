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
    FileName: JpaClientDetailsServiceImpl.java
    Date: 2019/3/12
    Author: lq
*/
package com.lq186.oauth2.service.jpa;

import com.lq186.common.util.EnumUtils;
import com.lq186.oauth2.entity.OAuth2Client;
import com.lq186.oauth2.repo.OAuth2ClientRepo;
import com.lq186.oauth2.state.OAuth2ClientState;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

public class JpaClientDetailsServiceImpl implements ClientDetailsService {

    private static final String CONTENT_SPLIT = ",";

    private final OAuth2ClientRepo clientRepo;

    public JpaClientDetailsServiceImpl(OAuth2ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Optional<OAuth2Client> optional = clientRepo.getByClientId(clientId);
        if (optional.isPresent()) {
            OAuth2Client oAuth2Client = optional.get();
            OAuth2ClientState state = EnumUtils.ofValue(OAuth2ClientState.class, oAuth2Client.getState(), "code");
            switch (state) {
                case NORMAL:
                    BaseClientDetails clientDetails = new BaseClientDetails();
                    clientDetails.setClientId(clientId);
                    clientDetails.setClientSecret(oAuth2Client.getClientSecret());
                    clientDetails.setRegisteredRedirectUri(new LinkedHashSet<>(Arrays.asList(oAuth2Client.getRedirectUrl().split(CONTENT_SPLIT))));
                    clientDetails.setAuthorizedGrantTypes(Arrays.asList(oAuth2Client.getGrantType().split(CONTENT_SPLIT)));
                    clientDetails.setScope(Arrays.asList(oAuth2Client.getScope().split(CONTENT_SPLIT)));
                    return clientDetails;
                case LOCKED:
                    throw new ClientRegistrationException(String.format("客户端[%s]已经被锁定", clientId));
                case DISABLED:
                    throw new ClientRegistrationException(String.format("客户端[%s]已经被禁用", clientId));
            }
        }
        throw new ClientRegistrationException(String.format("客户端[%s]不存在", clientId));
    }
}
