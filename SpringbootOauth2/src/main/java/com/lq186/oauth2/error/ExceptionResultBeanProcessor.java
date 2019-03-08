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
    FileName: ExceptionResultBeanProcessor.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.oauth2.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lq186.common.bean.ResultBean;
import com.lq186.common.springboot.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ExceptionResultBeanProcessor {

    public static final void process(ObjectMapper objectMapper, HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        ResultBean resultBean = new ResultBean(httpStatus.value());
        resultBean.setUrl(request.getServletPath());
        resultBean.setMsg(exception.getMessage());
        resultBean.setError(httpStatus.getReasonPhrase());
        WebUtils.addJsonUtf8Header(response);
        WebUtils.writeJsonUtf8String(response, objectMapper.writeValueAsString(resultBean));
    }

}
