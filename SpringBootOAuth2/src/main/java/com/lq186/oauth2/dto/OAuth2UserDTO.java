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
    FileName: OAuth2UserDTO.java
    Date: 2019/3/11
    Author: lq
*/
package com.lq186.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lq186.common.springboot.bean.AbstractDataTransferObject;
import com.lq186.common.util.EnumUtils;
import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.state.OAuth2UserState;

public class OAuth2UserDTO extends AbstractDataTransferObject<OAuth2User, OAuth2UserDTO> {

    @JsonProperty("username")
    private String username;

    @JsonProperty("pwd")
    private String pwd;

    @JsonProperty("show_name")
    private String showName;

    @JsonProperty("head_picture")
    private String headPicture;

    @JsonProperty("state")
    private Integer state;

    @JsonProperty("state_name")
    private String stateName;

    @JsonProperty("created_time")
    private Long createdTime;

    @JsonProperty("login_ip")
    private String loginIp;

    @JsonProperty("login_time")
    private Long loginTime;

    @Override
    protected void fromEntityAfterProperties(OAuth2User oAuth2User) {
        this.pwd = "******";
        this.stateName = EnumUtils.ofValue(OAuth2UserState.class, this.state, "code").getDisplay();
    }

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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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
