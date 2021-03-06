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
    FileName: JwtBearerGrant.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.auth;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.http.ResponseEntity;

public class JwtBearerGrant extends DefaultOAuthGrant {

    @Override
    protected GrantType getSupportedGrantType() {
        return GrantType.JWT_BEARER;
    }

    @Override
    protected ResponseEntity doGrant(OAuthTokenRequest tokenRequest) throws OAuthSystemException {
        return super.doGrant(tokenRequest);
    }
}
