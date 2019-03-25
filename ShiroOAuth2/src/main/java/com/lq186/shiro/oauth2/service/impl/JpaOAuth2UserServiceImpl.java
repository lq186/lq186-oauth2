package com.lq186.shiro.oauth2.service.impl;

import com.lq186.shiro.oauth2.enitty.OAuth2User;
import com.lq186.shiro.oauth2.repo.OAuth2UserRepo;
import com.lq186.shiro.oauth2.service.OAuth2UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class JpaOAuth2UserServiceImpl implements OAuth2UserService {

    @Resource
    private OAuth2UserRepo userRepo;

    @Override
    public Optional<OAuth2User> getByUsername(String username) {
        return userRepo.getByUsername(username);
    }

}
