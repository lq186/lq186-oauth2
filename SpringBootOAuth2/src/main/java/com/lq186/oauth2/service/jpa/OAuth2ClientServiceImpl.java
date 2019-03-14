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
    FileName: OAuth2ClientServiceImpl.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.service.jpa;

import com.lq186.oauth2.dto.OAuth2ClientDTO;
import com.lq186.oauth2.entity.OAuth2Client;
import com.lq186.oauth2.repo.OAuth2ClientRepo;
import com.lq186.oauth2.service.OAuth2ClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class OAuth2ClientServiceImpl implements OAuth2ClientService {

    @Resource
    private OAuth2ClientRepo clientRepo;

    @Override
    public Optional<OAuth2ClientDTO> getByClientId(String clientId) {
        Optional<OAuth2Client> optional = clientRepo.getByClientId(clientId);
        if (optional.isPresent()) {
            return Optional.of(new OAuth2ClientDTO(optional.get()));
        }
        return Optional.empty();
    }
}
