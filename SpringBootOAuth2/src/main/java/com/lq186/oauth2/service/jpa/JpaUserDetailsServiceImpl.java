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
    FileName: JpaUserDetailsServiceImpl.java
    Date: 2019/3/12
    Author: lq
*/
package com.lq186.oauth2.service.jpa;

import com.lq186.common.util.EnumUtils;
import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.repo.OAuth2UserRepo;
import com.lq186.oauth2.state.OAuth2UserState;
import com.lq186.oauth2.user.SimpleUserDetailsImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class JpaUserDetailsServiceImpl implements UserDetailsService {

    private OAuth2UserRepo userRepo;

    public JpaUserDetailsServiceImpl(OAuth2UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OAuth2User> optional = userRepo.getByUsername(username);
        if (optional.isPresent()) {
            OAuth2UserState state = EnumUtils.ofValue(OAuth2UserState.class, optional.get().getState(), "code");
            switch (state) {
                case NORMAL:
                    return new SimpleUserDetailsImpl(optional.get());
                case LOCKED:
                    throw new BadCredentialsException(String.format("用户[%s]已经被锁定", username));
                case DISABLED:
                    throw new BadCredentialsException(String.format("用户[%s]已经被禁用", username));
            }
            return new SimpleUserDetailsImpl(optional.get());
        }
        throw new UsernameNotFoundException(String.format("用户名[%s]不存在", username));
    }
}
