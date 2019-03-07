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

import com.lq186.oauth2.entity.PasswordUser;
import com.lq186.oauth2.state.UserStates;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SimpleUserDetailsImpl implements UserDetails {

    private final PasswordUser passwordUser;

    public SimpleUserDetailsImpl(PasswordUser passwordUser) {
        this.passwordUser = passwordUser;
    }

    @Override
    public String getUsername() {
        return passwordUser.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return passwordUser.getPwd();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNormal();
    }

    @Override
    public boolean isAccountNonLocked() {
        return null != passwordUser.getState() && UserStates.LOCKED.getCode() != passwordUser.getState().intValue();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAccountNormal();
    }

    @Override
    public boolean isEnabled() {
        return null != passwordUser.getState() && UserStates.DISABLED.getCode() != passwordUser.getState().intValue();
    }

    private boolean isAccountNormal() {
        return null != passwordUser.getState() && UserStates.NORMAL.getCode() == passwordUser.getState().intValue();
    }
}
