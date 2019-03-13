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
    FileName: OAuth2ClientDTO.java
    Date: 2019/3/11
    Author: lq
*/
package com.lq186.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lq186.common.springboot.bean.AbstractDataTransferObject;
import com.lq186.common.util.EnumUtils;
import com.lq186.oauth2.entity.OAuth2Client;
import com.lq186.oauth2.state.OAuth2ClientState;

public final class OAuth2ClientDTO extends AbstractDataTransferObject<OAuth2Client> {

    public OAuth2ClientDTO(OAuth2Client entity) {
        super(entity);
    }

    @JsonProperty("client_id")
    private String clientId; // 客户端ID

    @JsonProperty("client_secret")
    private String clientSecret; // 客户端密钥

    @JsonProperty("name")
    private String name; // 客户端名称

    @JsonProperty("state")
    private Integer state; // 客户端状态

    @JsonProperty("state_name")
    private String stateName; // 客户端状态显示内容

    @JsonProperty("grant_type")
    private String grantType; // 授权类型[authorization_code, client_credentials, password, implicit, refresh_token]

    @JsonProperty("scope")
    private String scope; // 授权范围

    @JsonProperty("redirect_url")
    private String redirectUrl; // 重定向地址

    @JsonProperty("created_time")
    private Long createdTime; // 创建时间

    @Override
    protected void afterProperties(OAuth2Client oAuth2Client) {
        this.clientSecret = "******";
        this.stateName = EnumUtils.ofValue(OAuth2ClientState.class, this.state, "code").getDisplay();
    }
}
