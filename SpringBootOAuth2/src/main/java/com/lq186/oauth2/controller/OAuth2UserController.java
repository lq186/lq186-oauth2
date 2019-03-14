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
    FileName: OAuth2UserController.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.controller;

import com.lq186.oauth2.dto.OAuth2UserDTO;
import com.lq186.oauth2.entity.OAuth2User;
import com.lq186.oauth2.user.SimpleUserDetailsImpl;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OAuth2UserController {

    /*
    @RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Principal user(Principal principal) {
        return principal;
    }
    */

    @RequestMapping(value = "/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public OAuth2UserDTO me(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            return getOauth2UserDTO((UsernamePasswordAuthenticationToken) principal);
        } else if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;
            Authentication authentication = auth2Authentication.getUserAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return getOauth2UserDTO((UsernamePasswordAuthenticationToken) authentication);
            }
            return null;
        }
        throw new RuntimeException("Unknown Principal");
    }

    private OAuth2UserDTO getOauth2UserDTO(UsernamePasswordAuthenticationToken authenticationToken) {
        SimpleUserDetailsImpl simpleUserDetails = (SimpleUserDetailsImpl) authenticationToken.getPrincipal();
        return new OAuth2UserDTO(simpleUserDetails.getOauth2User());
    }
}
