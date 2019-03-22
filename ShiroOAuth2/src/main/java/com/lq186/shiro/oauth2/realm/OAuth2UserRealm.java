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
    FileName: OAuth2UserRealm.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.realm;

import com.lq186.shiro.oauth2.enitty.OAuth2User;
import com.lq186.shiro.oauth2.service.OAuth2UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Optional;

public final class OAuth2UserRealm extends AuthorizingRealm {

    private final OAuth2UserService userService;

    public OAuth2UserRealm(OAuth2UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principalCollection) {
        final String username = (String) principalCollection.getPrimaryPrincipal();
        // TODO 加载权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authenticationToken) throws AuthenticationException {
        final String username = (String) authenticationToken.getPrincipal();
        Optional<OAuth2User> userOptional = userService.getByUsername(username);
        if (userOptional.isPresent()) {
            OAuth2User user = userOptional.get();
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user.getUsername(),
                    user.getPwd(),
                    ByteSource.Util.bytes(user.getSalt()),
                    getName()
            );
            return authenticationInfo;
        } else {
            throw new UnknownAccountException();
        }
    }
}
