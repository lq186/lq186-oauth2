package com.lq186.shiro.oauth2.service.impl;

import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import com.lq186.shiro.oauth2.repo.OAuth2ClientRepo;
import com.lq186.shiro.oauth2.service.OAuth2ClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class JpaOAuth2ClientServiceImpl implements OAuth2ClientService {

    @Resource
    private OAuth2ClientRepo clientRepo;

    @Override
    public Optional<OAuth2Client> getByClientId(String clientId) {
        return clientRepo.getByClientId(clientId);
    }
}
