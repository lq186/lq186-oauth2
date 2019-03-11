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
    Date: 2019/3/7
    Author: lq
*/
package com.lq186.oauth2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lq186.common.springboot.config.ConfigUtils;
import com.lq186.oauth2.service.mock.SimpleAuthorizationCodeServicesImpl;
import com.lq186.oauth2.service.mock.SimpleClientDetailServiceImpl;
import com.lq186.oauth2.service.mock.SimpleUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeanDefined {

    public static final String CLIENT_DETAILS_SERVICE = "customClientDetailsService";

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new SimpleUserDetailServiceImpl();
    }

    @Bean(BeanDefined.CLIENT_DETAILS_SERVICE)
    @Primary
    public ClientDetailsService clientDetailsService() {
        return new SimpleClientDetailServiceImpl();
    }

    @Bean
    @Primary
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(Collections.singletonList(provider));
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new SimpleAuthorizationCodeServicesImpl();
    }

    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(2));
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1));
        tokenServices.setReuseRefreshToken(false);
        return tokenServices;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return ConfigUtils.getObjectMapper();
    }

}
