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
    FileName: Oauth2ResultBeanExceptionSerializer.java
    Date: 2019/3/8
    Author: lq
*/
package com.lq186.oauth2.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lq186.common.bean.ResultBean;
import com.lq186.oauth2.exception.Oauth2ResultBeanException;

import java.io.IOException;

public class Oauth2ResultBeanExceptionSerializer extends StdSerializer<Oauth2ResultBeanException> {

    public Oauth2ResultBeanExceptionSerializer() {
        super(Oauth2ResultBeanException.class);
    }

    @Override
    public void serialize(Oauth2ResultBeanException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        ResultBean resultBean = value.getResultBean();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", resultBean.getCode());
        jsonGenerator.writeStringField("msg", resultBean.getMsg());
        jsonGenerator.writeStringField("error", resultBean.getError());
        jsonGenerator.writeStringField("path", resultBean.getUrl());
        /*
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jsonGenerator.writeStringField(key, add);
            }
        }
        */
        jsonGenerator.writeEndObject();
    }
}
