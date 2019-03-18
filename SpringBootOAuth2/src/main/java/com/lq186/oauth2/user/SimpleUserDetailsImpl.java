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

import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.state.OAuth2UserState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SimpleUserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final OAuth2User oauth2User;

    public SimpleUserDetailsImpl(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public String getUsername() {
        return oauth2User.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return oauth2User.getPwd();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNormal();
    }

    @Override
    public boolean isAccountNonLocked() {
        return null != oauth2User.getState() && OAuth2UserState.LOCKED.getCode() != oauth2User.getState().intValue();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNormal();
    }

    @Override
    public boolean isEnabled() {
        return null != oauth2User.getState() && OAuth2UserState.DISABLED.getCode() != oauth2User.getState().intValue();
    }

    private boolean isAccountNormal() {
        return null != oauth2User.getState() && OAuth2UserState.NORMAL.getCode() == oauth2User.getState().intValue();
    }

    public OAuth2User getOauth2User() {
        return oauth2User;
    }
}
