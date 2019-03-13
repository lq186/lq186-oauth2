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
    FileName: OAuth2User.java
    Date: 2019/3/7
    Author: lq
*/
package com.lq186.oauth2.entity;

import com.lq186.oauth2.consts.EntityMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = EntityMapping.OAUTH2_USER)
public final class OAuth2User extends EntityIdBean {

    @Column(name = "kwm_username", unique = true, nullable = false, length = 64)
    private String username; // 用户名，UK

    @Column(name = "kwm_pwd", nullable = false, length = 128)
    private String pwd; // 密码，非空

    @Column(name = "show_name", nullable = false, length = 128)
    private String showName; // 显示名称，非空

    @Column(name = "head_picture", length = 128)
    private String headPicture; // 用户头像，非空

    @Column(name = "kwm_state", nullable = false)
    private Integer state; // 状态，非空

    @Column(name = "created_time")
    private Long createdTime; // 创建（注册）时间，非空

    @Column(name = "login_ip", length = 48)
    private String loginIp; // 登录IP，可空

    @Column(name = "login_time")
    private Long loginTime; // 登录时间，可空

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getHeadPicture() {
        return headPicture;
    }

    public void setHeadPicture(String headPicture) {
        this.headPicture = headPicture;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
}
