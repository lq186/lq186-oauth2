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
    FileName: OAuth2Controller.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.thirdpart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lq186.common.springboot.rest.RestRequestBody;
import com.lq186.common.springboot.rest.RestResponseBody;
import com.lq186.common.springboot.rest.RestUtils;
import com.lq186.oauth2.thirdpart.consts.OAuth2Const;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OAuth2Controller {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/oauth2", produces = MediaType.TEXT_HTML_VALUE)
    public String oauth2(HttpServletRequest request, @RequestParam("code") String code, @RequestParam("state") String state) {
        RestRequestBody requestBody = new RestRequestBody();
        requestBody.setUrl(OAuth2Const.TOKEN_URL);
        requestBody.setMethod(HttpMethod.POST);
        requestBody.addHeader("Content-Type", "application/x-www-form-urlencoded");
        requestBody.addParameters(OAuth2Const.CLIENT_ID, OAuth2Const.CLIENT_ID_VALUE)
                .addParameters(OAuth2Const.CLIENT_SECRET, OAuth2Const.CLIENT_SECRET_VALUE)
                .addParameters(OAuth2Const.REDIRECT_URI, OAuth2Const.REDIRECT_URI_VALUE)
                .addParameters(OAuth2Const.SCOPE, OAuth2Const.SCOPE_READ)
                .addParameters(OAuth2Const.STATE, state)
                .addParameters(OAuth2Const.GRANT_TYPE, OAuth2Const.AUTHORIZATION_CODE)
                .addParameters(OAuth2Const.CODE, code);
        RestResponseBody responseBody = RestUtils.exchange(restTemplate, requestBody, HashMap.class);
        if (HttpStatus.OK == responseBody.getHttpStatus()) {
            request.setAttribute("token", responseBody.getBody());
            return "grant-success";
        } else {
            Map<String, String> errorMap;
            try {
                errorMap = objectMapper.readValue((byte[]) responseBody.getBody(), HashMap.class);
            } catch (Exception e) {
                errorMap = new HashMap<>();
            }
            request.setAttribute("error", errorMap);
            return "grant-fail";
        }
    }

}
