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
    FileName: DefaultOAuthGrant.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.auth;

import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public class DefaultOAuthGrant implements OAuthGrant {

    private OAuthGrant nextGrant;

    protected boolean isSupport(String grantTypeString) {
        GrantType grantType = getSupportedGrantType();
        return null != grantType && grantType.toString().equals(grantTypeString);
    }

    protected GrantType getSupportedGrantType() {
        return null;
    }

    @Override
    public ResponseEntity grant(final OAuthTokenRequest tokenRequest) throws OAuthSystemException {
        if (isSupport(tokenRequest.getGrantType())) {
            return doGrant(tokenRequest);
        } else {
            if (nextGrant != null) {
                return nextGrant.grant(tokenRequest);
            } else {
                return notSupportGrantResponse();
            }
        }
    }

    protected ResponseEntity doGrant(OAuthTokenRequest tokenRequest) throws OAuthSystemException {
        return notSupportGrantResponse();
    }

    private ResponseEntity notSupportGrantResponse() throws OAuthSystemException {
        if (null == responseEntity) {
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE)
                    .setErrorDescription(ErrorDescriptions.UNSUPPORTED_GRANT_TYPE).buildJSONMessage();
            responseEntity = new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
        }
        return responseEntity;
    }

    public void setNextGrant(OAuthGrant nextGrant) {
        this.nextGrant = nextGrant;
    }

    protected OAuthGrant getNextGrant() {
        return nextGrant;
    }

    private static ResponseEntity responseEntity;

}
