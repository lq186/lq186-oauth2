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
    FileName: ResultBeanOAuth2AccessToken.java
    Date: 2019/3/11
    Author: lq
*/
package com.lq186.oauth2.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lq186.oauth2.json.ResultBeanOAuth2AccessTokenDeserializer;
import com.lq186.oauth2.json.ResultBeanOAuth2AccessTokenSerializer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.io.Serializable;
import java.util.*;

@JsonSerialize(using = ResultBeanOAuth2AccessTokenSerializer.class)
@JsonDeserialize(using = ResultBeanOAuth2AccessTokenDeserializer.class)
public class ResultBeanOAuth2AccessToken implements Serializable, OAuth2AccessToken {

    private static final String TOKEN_TYPE = "custom";

    private String value;

    private Date expiration;

    private String tokenType;

    private OAuth2RefreshToken refreshToken;

    private Set<String> scope;

    private Map<String, Object> additionalInformation;

    public ResultBeanOAuth2AccessToken(String value) {
        this.tokenType = TOKEN_TYPE;
        this.additionalInformation = Collections.emptyMap();
        this.value = value;
    }

    public ResultBeanOAuth2AccessToken(OAuth2AccessToken accessToken) {
        this(accessToken.getValue());
        this.setAdditionalInformation(accessToken.getAdditionalInformation());
        this.setRefreshToken(accessToken.getRefreshToken());
        this.setExpiration(accessToken.getExpiration());
        this.setScope(accessToken.getScope());
        this.setTokenType(accessToken.getTokenType());
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public int getExpiresIn() {
        return this.expiration != null ? Long.valueOf((this.expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue() : 0;
    }

    protected void setExpiresIn(int delta) {
        this.setExpiration(new Date(System.currentTimeMillis() + (long) delta));
    }

    public Date getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public boolean isExpired() {
        return this.expiration != null && this.expiration.before(new Date());
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public OAuth2RefreshToken getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(OAuth2RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Set<String> getScope() {
        return this.scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public boolean equals(Object obj) {
        return obj != null && this.toString().equals(obj.toString());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        return String.valueOf(this.getValue());
    }

    public static OAuth2AccessToken valueOf(Map<String, String> tokenParams) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken((String) tokenParams.get("access_token"));
        if (tokenParams.containsKey("expires_in")) {
            long expiration = 0L;

            try {
                expiration = Long.parseLong(String.valueOf(tokenParams.get("expires_in")));
            } catch (NumberFormatException var5) {
                ;
            }

            token.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L));
        }

        if (tokenParams.containsKey("refresh_token")) {
            String refresh = (String) tokenParams.get("refresh_token");
            DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(refresh);
            token.setRefreshToken(refreshToken);
        }

        if (tokenParams.containsKey("scope")) {
            Set<String> scope = new TreeSet();
            StringTokenizer tokenizer = new StringTokenizer((String) tokenParams.get("scope"), " ,");

            while (tokenizer.hasMoreTokens()) {
                scope.add(tokenizer.nextToken());
            }

            token.setScope(scope);
        }

        if (tokenParams.containsKey("token_type")) {
            token.setTokenType((String) tokenParams.get("token_type"));
        }

        return token;
    }

    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = new LinkedHashMap(additionalInformation);
    }


}
