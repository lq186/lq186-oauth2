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
    FileName: AuthorizationCodeGrant.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.auth;

import com.lq186.common.util.StringUtils;
import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import com.lq186.shiro.oauth2.service.AuthorizationCodeService;
import com.lq186.shiro.oauth2.util.ResponseUtils;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public class AuthorizationCodeGrant extends DefaultOAuthGrant {

    private final AuthorizationCodeService authorizationCodeService;

    public AuthorizationCodeGrant(AuthorizationCodeService authorizationCodeService) {
        this.authorizationCodeService = authorizationCodeService;
    }

    @Override
    protected GrantType getSupportedGrantType() {
        return GrantType.AUTHORIZATION_CODE;
    }

    @Override
    protected ResponseEntity doGrant(OAuthTokenRequest tokenRequest) throws OAuthSystemException {
        String code = tokenRequest.getParam(OAuth.OAUTH_CODE);
        OAuthToken oAuthToken;
        if (StringUtils.isBlank(code) || (null == (oAuthToken = authorizationCodeService.consumeAuthorizationCode(code)))) {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST,
                    OAuthError.TokenResponse.INVALID_GRANT,
                    ErrorDescriptions.INVALID_CODE);
        }
        return ResponseUtils.success(oAuthToken);
    }
}
