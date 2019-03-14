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

import com.lq186.common.springboot.rest.RestRequestBody;
import com.lq186.common.springboot.rest.RestResponseBody;
import com.lq186.common.springboot.rest.RestUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class OAuth2Controller {

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String SCOPE = "scope";
    private static final String STATE = "state";
    private static final String CODE = "code";
    private static final String GRANT_TYPE = "grant_type";

    private static final String CLIENT_ID_VALUE = "92498c196acc4c74a346f0235c3b9c4c";
    private static final String CLIENT_SECRET_VALUE = "123456";
    private static final String REDIRECT_URI_VALUE = "http://127.0.0.1:9080/oauth2";
    private static final String SCOPE_VALUE = "read";
    private static final String GRANT_TYPE_VALUE = "authorization_code";
    private static final String TOKEN_URL = "http://127.0.0.1:8080/oauth/token";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping(value = "/oauth2", produces = MediaType.TEXT_HTML_VALUE)
    public String oauth2(HttpServletRequest request, @RequestParam("code") String code, @RequestParam("state") String state) {
        RestRequestBody requestBody = new RestRequestBody();
        requestBody.setUrl(TOKEN_URL);
        requestBody.setMethod(HttpMethod.POST);
        requestBody.addParameters(CLIENT_ID, CLIENT_ID_VALUE)
                .addParameters(CLIENT_SECRET, CLIENT_SECRET_VALUE)
                .addParameters(REDIRECT_URI, REDIRECT_URI_VALUE)
                .addParameters(SCOPE, SCOPE_VALUE)
                .addParameters(STATE, state)
                .addParameters(GRANT_TYPE, GRANT_TYPE_VALUE)
                .addParameters(CODE, code);
        RestResponseBody<HashMap> responseBody = RestUtils.exchange(restTemplate, requestBody, HashMap.class);
        if (HttpStatus.OK == responseBody.getHttpStatus()) {
            request.setAttribute("token", responseBody.getBody());
            return "grant-success";
        } else {
            return "grant-fail";
        }
    }

}
