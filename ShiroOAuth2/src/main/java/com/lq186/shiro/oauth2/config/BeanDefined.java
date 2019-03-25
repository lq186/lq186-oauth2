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
    FileName: BeanDefined.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.config;

import com.lq186.shiro.oauth2.auth.*;
import com.lq186.shiro.oauth2.realm.OAuth2UserRealm;
import com.lq186.shiro.oauth2.service.AuthorizationCodeService;
import com.lq186.shiro.oauth2.service.OAuth2ClientService;
import com.lq186.shiro.oauth2.service.OAuth2UserService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class BeanDefined {

    @Resource
    private OAuth2UserService userService;

    @Resource
    private OAuth2ClientService clientService;

    @Resource
    private AuthorizationCodeService authorizationCodeService;

    @Bean
    public OAuthIssuer oAuthIssuer() {
        return new OAuthIssuerImpl(new MD5Generator());
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(new OAuth2UserRealm(userService));
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    @PostConstruct
    public void postConstruct() {
        OAuthGrantChainContext.addOAuthGrant(new BaseCheckGrant(clientService));
        OAuthGrantChainContext.addOAuthGrant(new ClientCredentialsGrant(clientService));
        OAuthGrantChainContext.addOAuthGrant(new AuthorizationCodeGrant(authorizationCodeService));
        OAuthGrantChainContext.addOAuthGrant(new RefreshTokenGrant());
        OAuthGrantChainContext.addOAuthGrant(new PasswordGrant(userService));
        OAuthGrantChainContext.addOAuthGrant(new ImplicitGrant(userService));
        OAuthGrantChainContext.addOAuthGrant(new JwtBearerGrant());
    }

}
