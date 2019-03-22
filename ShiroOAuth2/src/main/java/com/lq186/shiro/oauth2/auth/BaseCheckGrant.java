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
    FileName: BaseCheckGrant.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.auth;

import com.lq186.common.util.StringUtils;
import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import com.lq186.shiro.oauth2.service.OAuth2ClientService;
import com.lq186.shiro.oauth2.util.ResponseUtils;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class BaseCheckGrant extends DefaultOAuthGrant {

    private final OAuth2ClientService clientService;

    public BaseCheckGrant(OAuth2ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    protected boolean isSupport(String grantTypeString) {
        return true;
    }

    @Override
    public ResponseEntity grant(OAuthTokenRequest tokenRequest) throws OAuthSystemException {
        if (StringUtils.isBlank(tokenRequest.getClientId())) {
            return ResponseUtils.errorClientIdResponse();
        }

        Optional<OAuth2Client> clientOptional = clientService.getByClientId(tokenRequest.getClientId());
        if (!clientOptional.isPresent()) {
            return ResponseUtils.errorClientIdResponse();
        }

        OAuth2Client client = clientOptional.get();
        if (StringUtils.isBlank(tokenRequest.getClientSecret()) || !tokenRequest.getClientSecret().equals(client.getClientSecret())) {
            return ResponseUtils.errorResponse(HttpServletResponse.SC_UNAUTHORIZED, OAuthError.TokenResponse.UNAUTHORIZED_CLIENT,
                    ErrorDescriptions.INVALID_CLIENT);
        }

        return getNextGrant().grant(tokenRequest);
    }

}
