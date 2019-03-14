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
    FileName: OAuth2GrantController.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.controller;

import com.lq186.common.log.Log;
import com.lq186.oauth2.consts.SessionAttribute;
import com.lq186.oauth2.dto.OAuth2ClientDTO;
import com.lq186.oauth2.service.OAuth2ClientService;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Controller
@SessionAttributes(SessionAttribute.AUTHORIZATION_REQUEST)
public class OAuth2GrantController {

    private static final Log log = Log.getLog(OAuth2GrantController.class);

    private static final String GRANT_PAGE_NAME = "grant";

    private static final String REQUEST_ATTRIBUTE_CLIENT = "client";

    private static final String REQUEST_ATTRIBUTE_SCOPES = "scopes";

    @Resource
    private OAuth2ClientService clientService;

    @RequestMapping(value = "/oauth/confirm_access", produces = MediaType.TEXT_HTML_VALUE)
    public String confirmAccess(HttpServletRequest request, Map<String, Object> model) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get(SessionAttribute.AUTHORIZATION_REQUEST);
        String clientId = authorizationRequest.getClientId();
        Optional<OAuth2ClientDTO> optional = clientService.getByClientId(clientId);
        if (optional.isPresent()) {
            request.setAttribute(REQUEST_ATTRIBUTE_SCOPES, authorizationRequest.getScope());
            request.setAttribute(REQUEST_ATTRIBUTE_CLIENT, optional.get());
        } else {
            throw new ClientRegistrationException(String.format("客户端[%s]不存在", clientId));
        }
        return GRANT_PAGE_NAME;
    }

}
