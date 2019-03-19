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
    FileName: OpenIdUserAuthenticationConverter.java
    Date: 2019/3/19
    Author: lq
*/
package com.lq186.oauth2.resource.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Map;

public class OpenIdUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        Authentication authentication = super.extractAuthentication(map);
        OpenIdPrincipal principal = new OpenIdPrincipal();
        principal.setUsername(String.valueOf(map.get("user_name")));
        principal.setOpenid(String.valueOf(map.get("openid")));
        return new UsernamePasswordAuthenticationToken(principal, "N/A", authentication.getAuthorities());
    }

}
