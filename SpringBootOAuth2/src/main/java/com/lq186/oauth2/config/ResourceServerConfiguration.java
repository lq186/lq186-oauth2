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
    FileName: ResourceServerConfiguration.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/*
@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 先定义需要通过的URL
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/oauth/**", "/error", "/css/**", "/js/**", "/image/**", "/favicon.ico").permitAll()
                .and()
                .formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll()
                .and()
                // 最后定义其他所有的URL均需要保护
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable() //.ignoringAntMatchers("/h2-console/**") // 对特定路径关闭csrf跨域拦截
                //.and()
                .headers().frameOptions().sameOrigin(); // 同源允许IFrame
    }
}
*/
