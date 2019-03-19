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
    FileName: UserDetailsUtils.java
    Date: 2019/3/19
    Author: lq
*/
package com.lq186.oauth2.util;

import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.user.SimpleUserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public final class UserDetailsUtils {

    /**
     * 转换OAuth2User对象为UserDetails实现
     *
     * @param user
     * @return UserDetails 非空
     */
    public static final UserDetails fromOAuth2User(OAuth2User user) {
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        SimpleUserDetailsImpl simpleUserDetails = new SimpleUserDetailsImpl();
        BeanUtils.copyProperties(user, simpleUserDetails);
        return simpleUserDetails;
    }

}
