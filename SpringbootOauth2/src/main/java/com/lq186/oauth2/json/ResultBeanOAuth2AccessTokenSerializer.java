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
    FileName: ResultBeanOAuth2AccessTokenSerializer.java
    Date: 2019/3/11
    Author: lq
*/
package com.lq186.oauth2.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lq186.common.code.ResultCode;
import com.lq186.oauth2.bean.ResultBeanOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ResultBeanOAuth2AccessTokenSerializer extends StdSerializer<ResultBeanOAuth2AccessToken> {

    public ResultBeanOAuth2AccessTokenSerializer() {
        super(ResultBeanOAuth2AccessToken.class);
    }

    @Override
    public void serialize(ResultBeanOAuth2AccessToken token, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", ResultCode.SUCCESS);
        jsonGenerator.writeObjectFieldStart("data");
        jsonGenerator.writeStringField("access_token", token.getValue());
        jsonGenerator.writeStringField("token_type", token.getTokenType());
        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            jsonGenerator.writeStringField("refresh_token", refreshToken.getValue());
        }

        Date expiration = token.getExpiration();
        if (expiration != null) {
            long now = System.currentTimeMillis();
            jsonGenerator.writeNumberField("expires_in", (expiration.getTime() - now) / 1000L);
        }

        Set<String> scope = token.getScope();
        Iterator var8;
        String key;
        if (scope != null && !scope.isEmpty()) {
            StringBuffer scopes = new StringBuffer();
            var8 = scope.iterator();

            while(var8.hasNext()) {
                key = (String)var8.next();
                Assert.hasLength(key, "Scopes cannot be null or empty. Got " + scope + "");
                scopes.append(key);
                scopes.append(" ");
            }

            jsonGenerator.writeStringField("scope", scopes.substring(0, scopes.length() - 1));
        }

        Map<String, Object> additionalInformation = token.getAdditionalInformation();
        var8 = additionalInformation.keySet().iterator();

        while(var8.hasNext()) {
            key = (String)var8.next();
            jsonGenerator.writeObjectField(key, additionalInformation.get(key));
        }
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
