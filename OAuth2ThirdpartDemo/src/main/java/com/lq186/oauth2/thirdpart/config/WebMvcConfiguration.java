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
    FileName: WebMvcConfiguration.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.thirdpart.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lq186.common.springboot.config.BaseWebMvcConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfiguration extends BaseWebMvcConfiguration {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
