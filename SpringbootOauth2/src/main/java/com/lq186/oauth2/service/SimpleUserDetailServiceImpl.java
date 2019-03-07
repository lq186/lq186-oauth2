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
    FileName: SimpleUserDetailServiceImpl.java
    Date: 2019/3/7
    Author: lq
*/
package com.lq186.oauth2.service;

import com.lq186.common.util.RandomUtils;
import com.lq186.oauth2.entity.PasswordUser;
import com.lq186.oauth2.state.UserStates;
import com.lq186.oauth2.user.SimpleUserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SimpleUserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PasswordUser passwordUser = new PasswordUser();
        passwordUser.setId(1L);
        passwordUser.setBzid(RandomUtils.randomUUID());
        passwordUser.setUsername(username);
        passwordUser.setPwd("{noop}123456");
        passwordUser.setShowName(RandomUtils.randomString(5));
        passwordUser.setHeadPicture(String.format("/images/head/%s.jpg", passwordUser.getBzid()));
        passwordUser.setState(UserStates.NORMAL.getCode());
        passwordUser.setCreatedTime(System.currentTimeMillis());
        return new SimpleUserDetailsImpl(passwordUser);
    }

}
