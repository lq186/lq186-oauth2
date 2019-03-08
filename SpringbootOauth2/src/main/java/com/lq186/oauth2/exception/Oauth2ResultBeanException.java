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
    FileName: Oauth2ResultBeanException.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.oauth2.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lq186.common.bean.ResultBean;
import com.lq186.oauth2.json.Oauth2ResultBeanExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = Oauth2ResultBeanExceptionSerializer.class)
public class Oauth2ResultBeanException extends OAuth2Exception {

    private ResultBean resultBean;

    public Oauth2ResultBeanException(String msg) {
        super(msg);
    }

    public Oauth2ResultBeanException(String msg, Throwable t) {
        super(msg, t);
    }

    public Oauth2ResultBeanException(ResultBean resultBean) {
        super(resultBean.getMsg());
        this.resultBean = resultBean;
    }

    public ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(ResultBean resultBean) {
        this.resultBean = resultBean;
    }
}
