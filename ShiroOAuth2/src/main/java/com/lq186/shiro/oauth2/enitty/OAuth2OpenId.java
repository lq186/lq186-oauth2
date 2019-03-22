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
    FileName: OAuth2OpenId.java
    Date: 2019/3/19
    Author: lq
*/
package com.lq186.shiro.oauth2.enitty;

import com.lq186.shiro.oauth2.consts.EntityMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = EntityMapping.OAUTH2_OPENID)
public class OAuth2OpenId extends EntityIdBean {

    @Column(name = "client_bzid", length = 32, nullable = false)
    private String clientBzid;

    @Column(name = "user_bzid", length = 32, nullable = false)
    private String userBzid;

    @Column(name = "openid", length = 32, unique = true, nullable = false)
    private String openid;

    @Column(name = "created_time", nullable = false)
    private Long createdTime;

    public String getClientBzid() {
        return clientBzid;
    }

    public void setClientBzid(String clientBzid) {
        this.clientBzid = clientBzid;
    }

    public String getUserBzid() {
        return userBzid;
    }

    public void setUserBzid(String userBzid) {
        this.userBzid = userBzid;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
