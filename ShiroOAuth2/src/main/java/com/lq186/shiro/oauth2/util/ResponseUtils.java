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
    FileName: ResponseUtils.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.util;

import com.lq186.common.util.StringUtils;
import com.lq186.shiro.oauth2.bean.AdditionalInfoOAuthToken;
import com.lq186.shiro.oauth2.consts.ErrorDescriptions;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public final class ResponseUtils {

    public static final ResponseEntity errorResponse(int statusCode, String error, String errorDescription) throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(statusCode)
                .setError(error)
                .setErrorDescription(errorDescription)
                .buildJSONMessage();
        return formOAuthResponse(response);
    }

    public static final ResponseEntity errorResponse(int statusCode, OAuthProblemException e) throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(statusCode).error(e).buildJSONMessage();
        return formOAuthResponse(response);
    }

    public static ResponseEntity errorClientIdResponse() throws OAuthSystemException {
        return ResponseUtils.errorResponse(HttpServletResponse.SC_BAD_REQUEST, OAuthError.TokenResponse.INVALID_CLIENT,
                ErrorDescriptions.INVALID_CLIENT);
    }

    public static ResponseEntity success(OAuthToken oAuthToken) throws OAuthSystemException {
        OAuthASResponse.OAuthTokenResponseBuilder builder = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK);

        if (!StringUtils.isBlank(oAuthToken.getAccessToken())) {
            builder.setAccessToken(oAuthToken.getAccessToken());
        }

        if (null != oAuthToken.getExpiresIn() && oAuthToken.getExpiresIn() > 0) {
            builder.setExpiresIn(String.valueOf(oAuthToken.getExpiresIn()));
        }

        if (!StringUtils.isBlank(oAuthToken.getTokenType())) {
            builder.setTokenType(oAuthToken.getTokenType());
        }

        if (!StringUtils.isBlank(oAuthToken.getRefreshToken())) {
            builder.setRefreshToken(oAuthToken.getRefreshToken());
        }

        if (!StringUtils.isBlank(oAuthToken.getScope())) {
            builder.setScope(oAuthToken.getScope());
        }

        if (oAuthToken instanceof AdditionalInfoOAuthToken) {
            Map<String, String> additionalInfo = ((AdditionalInfoOAuthToken) oAuthToken).getAdditionalInfo();
            if (null != additionalInfo && additionalInfo.size() > 0) {
                for (Map.Entry<String, String> entry : additionalInfo.entrySet()) {
                    if (null != entry.getValue()) {
                        builder.setParam(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        return formOAuthResponse(builder.buildJSONMessage());
    }

    private static final ResponseEntity formOAuthResponse(OAuthResponse response) {
        return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    }
}
