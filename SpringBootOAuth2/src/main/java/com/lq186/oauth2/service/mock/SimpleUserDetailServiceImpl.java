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
package com.lq186.oauth2.service.mock;

import com.lq186.common.util.RandomUtils;
import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.state.OAuth2UserState;
import com.lq186.oauth2.user.SimpleUserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SimpleUserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuth2User oauth2User = new OAuth2User();
        oauth2User.setId(1L);
        oauth2User.setBzid(RandomUtils.randomUUID());
        oauth2User.setUsername(username);
        oauth2User.setPwd("{bcrypt}$2a$10$uUQbQS3fAV3jjfCiRulm7.ses2bfOVV5dizbfSrEbwj.5F4dGzHHq"); // 123456
        oauth2User.setShowName(RandomUtils.randomString(5));
        oauth2User.setHeadPicture(String.format("/images/head/%s.jpg", oauth2User.getBzid()));
        oauth2User.setState(OAuth2UserState.NORMAL.getCode());
        oauth2User.setCreatedTime(System.currentTimeMillis());
        return new SimpleUserDetailsImpl(oauth2User);
    }

}
