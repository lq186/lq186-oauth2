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
    FileName: ResultBeanOAuth2AccessTokenDeserializer.java
    Date: 2019/3/11
    Author: lq
*/
package com.lq186.oauth2.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lq186.oauth2.bean.ResultBeanOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

public class ResultBeanOAuth2AccessTokenDeserializer extends StdDeserializer<ResultBeanOAuth2AccessToken> {

    public ResultBeanOAuth2AccessTokenDeserializer() {
        super(ResultBeanOAuth2AccessToken.class);
    }

    @Override
    public ResultBeanOAuth2AccessToken deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String tokenValue = null;
        String tokenType = null;
        String refreshToken = null;
        Long expiresIn = null;
        Set<String> scope = null;
        LinkedHashMap additionalInformation = new LinkedHashMap();

        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String name = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if ("access_token".equals(name)) {
                tokenValue = jsonParser.getText();
            } else if ("token_type".equals(name)) {
                tokenType = jsonParser.getText();
            } else if ("refresh_token".equals(name)) {
                refreshToken = jsonParser.getText();
            } else if ("expires_in".equals(name)) {
                try {
                    expiresIn = jsonParser.getLongValue();
                } catch (JsonParseException var11) {
                    expiresIn = Long.valueOf(jsonParser.getText());
                }
            } else if ("scope".equals(name)) {
                scope = this.parseScope(jsonParser);
            } else {
                additionalInformation.put(name, jsonParser.readValueAs(Object.class));
            }
        }

        ResultBeanOAuth2AccessToken accessToken = new ResultBeanOAuth2AccessToken(tokenValue);
        accessToken.setTokenType(tokenType);
        if (expiresIn != null) {
            accessToken.setExpiration(new Date(System.currentTimeMillis() + expiresIn * 1000L));
        }

        if (refreshToken != null) {
            accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(refreshToken));
        }

        accessToken.setScope(scope);
        accessToken.setAdditionalInformation(additionalInformation);
        return accessToken;
    }

    private Set<String> parseScope(JsonParser jsonParser) throws JsonParseException, IOException {
        Object scope;
        if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
            scope = new TreeSet();

            while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
                ((Set)scope).add(jsonParser.getValueAsString());
            }
        } else {
            String text = jsonParser.getText();
            scope = OAuth2Utils.parseParameterList(text);
        }

        return (Set)scope;
    }
}
