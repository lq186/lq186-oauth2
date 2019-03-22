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
    FileName: ErrorDescriptions.java
    Date: 2019/3/22
    Author: lq
*/
package com.lq186.shiro.oauth2.consts;

public final class ErrorDescriptions {

    public static final String INVALID_CLIENT = "错误的客户端凭证: client_id OR client_secret";

    public static final String UNSUPPORTED_GRANT_TYPE = "不支持的授权类型";

    public static final String INVALID_CODE = "错误的授权码";

    public static final String INVALID_USERNAME_OR_PASSWORD = "用户名或密码错误";

    public static final String UNSUPPORTED_HTTP_METHOD = "不支持的HTTP方法";

}
