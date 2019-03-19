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
    FileName: SimpleUserDetailsImpl.java
    Date: 2019/3/7
    Author: lq
*/
package com.lq186.oauth2.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lq186.oauth2.state.OAuth2UserState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@JsonIgnoreProperties({"pwd", "password"})
public class SimpleUserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;

    private String pwd;

    private String showName;

    private String headPicture;

    private Integer state;

    private Long createdTime;

    private String loginIp;

    private Long loginTime;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNormal();
    }

    @Override
    public boolean isAccountNonLocked() {
        return null != state && OAuth2UserState.LOCKED.getCode() != state.intValue();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNormal();
    }

    @Override
    public boolean isEnabled() {
        return null != state && OAuth2UserState.DISABLED.getCode() != state.intValue();
    }

    private boolean isAccountNormal() {
        return null != state && OAuth2UserState.NORMAL.getCode() == state.intValue();
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
