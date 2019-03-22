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
    FileName: OAuth2Client.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.shiro.oauth2.enitty;

import com.lq186.shiro.oauth2.consts.EntityMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = EntityMapping.OAUTH2_CLIENT)
public class OAuth2Client extends EntityIdBean {

    @Column(name = "client_id", unique = true, nullable = false, length = 32)
    private String clientId; // 客户端ID

    @Column(name = "client_secret", nullable = false, length = 128)
    private String clientSecret; // 客户端密钥

    @Column(name = "kwm_name", nullable = false, length = 128)
    private String name; // 客户端名称

    @Column(name = "logo", nullable = false, length = 128)
    private String logo; // 客户端LOGO

    @Column(name = "kwm_state", nullable = false)
    private Integer state; // 客户端状态

    @Column(name = "grant_type", nullable = false, length = 128)
    private String grantType; // 授权类型[authorization_code, client_credentials, password, implicit, refresh_token]

    @Column(name = "kwm_scope", nullable = false, length = 128)
    private String scope; // 授权范围

    @Column(name = "redirect_url", nullable = false, length = 250)
    private String redirectUrl; // 重定向地址

    @Column(name = "created_time", nullable = false)
    private Long createdTime; // 创建时间

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
}
