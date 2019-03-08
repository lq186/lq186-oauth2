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
    FileName: ResultBeanExceptionTranslator.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.oauth2.handler;

import com.lq186.common.bean.ResultBean;
import com.lq186.common.code.ResultCode;
import com.lq186.common.i18n.MessageKey;
import com.lq186.oauth2.exception.Oauth2ResultBeanException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

public class ResultBeanExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        ResultBean resultBean = null;
        if (e instanceof OAuth2Exception) {
            resultBean = new ResultBean(((OAuth2Exception) e).getHttpErrorCode());
        } else {
            resultBean = new ResultBean(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        if (HttpStatus.BAD_REQUEST.value() == resultBean.getCode()) {
            resultBean.setMsg(MessageKey.USER_LOGIN_ERROR);
        } else {
            resultBean.setMsg(MessageKey.SYSTEM_ERROR);
        }
        resultBean.setError(e.getMessage());
        return ResponseEntity.status(resultBean.getCode()).body(new Oauth2ResultBeanException(resultBean));
    }

}
