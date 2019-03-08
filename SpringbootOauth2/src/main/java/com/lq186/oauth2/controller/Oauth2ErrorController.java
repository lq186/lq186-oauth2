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
    FileName: Oauth2ErrorController.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.oauth2.controller;

import com.lq186.common.bean.ResultBean;
import com.lq186.common.springboot.controller.BaseErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class Oauth2ErrorController extends BaseErrorController {

    @Resource
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = ERROR_PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public ResultBean handleError(HttpServletRequest request) {
        return super.handleError(request);
    }

    @Override
    protected ErrorAttributes getErrorAttributes() {
        return errorAttributes;
    }

}
