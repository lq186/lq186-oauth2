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
    FileName: OAuth2UserState.java
    Date: 2019/3/7
    Author: lq
*/
package com.lq186.oauth2.state;

public enum OAuth2ClientState {

    NORMAL(0, "正常"), LOCKED(1, "锁定"), DISABLED(2, "禁用");

    private final int code;

    private final String display;

    OAuth2ClientState(int code, String display) {
        this.code = code;
        this.display = display;
    }

    public int getCode() {
        return code;
    }

    public String getDisplay() {
        return display;
    }
}
