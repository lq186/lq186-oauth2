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
    FileName: SimpleAuthorizationCodeServicesImpl.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.oauth2.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.HashMap;
import java.util.Map;

public class SimpleAuthorizationCodeServicesImpl extends RandomValueAuthorizationCodeServices {

    private static final Map<String, OAuth2Authentication> AUTHENTICATION_MAP = new HashMap<>();

    @Override
    protected void store(String code, OAuth2Authentication oAuth2Authentication) {
        AUTHENTICATION_MAP.put(code, oAuth2Authentication);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        return AUTHENTICATION_MAP.remove(code);
    }
}
