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
    FileName: SimpleClientDetailServiceImpl.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.oauth2.service.mock;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class SimpleClientDetailServiceImpl implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret("{noop}123456");
        clientDetails.setRegisteredRedirectUri(new LinkedHashSet<>(Arrays.asList("http://www.baidu.com/", "http://www.google.com/")));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "client_credentials", "password", "implicit", "refresh_token"));
        clientDetails.setScope(Arrays.asList("read", "write"));
        return clientDetails;
    }
}
