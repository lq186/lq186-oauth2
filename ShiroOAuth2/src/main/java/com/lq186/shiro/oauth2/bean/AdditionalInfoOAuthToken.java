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
    FileName: AdditionalInfoOAuthToken.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.bean;

import com.lq186.common.util.StringUtils;
import org.apache.oltu.oauth2.common.token.BasicOAuthToken;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdditionalInfoOAuthToken extends BasicOAuthToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> additionalInfo;

    public AdditionalInfoOAuthToken() {
    }

    public AdditionalInfoOAuthToken(String accessToken, String tokenType, Long expiresIn, String refreshToken, String scope) {
        super(accessToken, tokenType, expiresIn, refreshToken, scope);
    }

    public AdditionalInfoOAuthToken(String accessToken, String tokenType) {
        super(accessToken, tokenType);
    }

    public AdditionalInfoOAuthToken(String accessToken, String tokenType, Long expiresIn) {
        super(accessToken, tokenType, expiresIn);
    }

    public AdditionalInfoOAuthToken(String accessToken, String tokenType, Long expiresIn, String scope) {
        super(accessToken, tokenType, expiresIn, scope);
    }

    public Map<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Map<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public synchronized void addAdditionalInfo(String key, String value) {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
            if (null == additionalInfo) {
                additionalInfo = new LinkedHashMap<>();
            }
            additionalInfo.put(key, value);
        }
    }
}
