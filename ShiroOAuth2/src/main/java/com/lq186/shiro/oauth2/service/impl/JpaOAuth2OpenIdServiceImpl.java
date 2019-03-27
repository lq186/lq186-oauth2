package com.lq186.shiro.oauth2.service.impl;

import com.lq186.common.util.RandomUtils;
import com.lq186.shiro.oauth2.enitty.OAuth2Client;
import com.lq186.shiro.oauth2.enitty.OAuth2OpenId;
import com.lq186.shiro.oauth2.enitty.OAuth2User;
import com.lq186.shiro.oauth2.repo.OAuth2ClientRepo;
import com.lq186.shiro.oauth2.repo.OAuth2OpenIdRepo;
import com.lq186.shiro.oauth2.repo.OAuth2UserRepo;
import com.lq186.shiro.oauth2.service.OAuth2OpenIdService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class JpaOAuth2OpenIdServiceImpl implements OAuth2OpenIdService {

    @Resource
    private OAuth2OpenIdRepo openIdRepo;

    @Resource
    private OAuth2ClientRepo clientRepo;

    @Resource
    private OAuth2UserRepo userRepo;

    @Transactional
    @Override
    public OAuth2OpenId getOpenIdCreateNewIfNotExists(@NotNull OAuth2Client client, @NotNull OAuth2User user) {

        String clientBzid = client.getBzid();
        String userBzid = user.getBzid();

        Optional<OAuth2OpenId> openIdOptional = openIdRepo.getByClientBzidAndUserBzid(clientBzid, userBzid);
        if (openIdOptional.isPresent()) {
            return openIdOptional.get();
        }

        String openid = DigestUtils.md5Hex(clientBzid + userBzid);
        OAuth2OpenId oAuth2OpenId = new OAuth2OpenId();
        oAuth2OpenId.setBzid(RandomUtils.randomUUID());
        oAuth2OpenId.setClientBzid(clientBzid);
        oAuth2OpenId.setUserBzid(userBzid);
        oAuth2OpenId.setOpenid(openid);
        oAuth2OpenId.setCreatedTime(System.currentTimeMillis());

        return openIdRepo.saveAndFlush(oAuth2OpenId);
    }

}
