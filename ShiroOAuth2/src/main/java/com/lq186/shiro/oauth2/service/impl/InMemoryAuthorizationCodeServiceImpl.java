package com.lq186.shiro.oauth2.service.impl;

import com.lq186.common.util.RandomUtils;
import com.lq186.shiro.oauth2.service.AuthorizationCodeService;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public final class InMemoryAuthorizationCodeServiceImpl implements AuthorizationCodeService {

    private static final Map<String, OAuthToken> TOKEN_MAP = new HashMap<>();

    @Override
    public String createAuthorizationCode(String clientId, OAuthToken authToken) {
        String code = RandomUtils.randomString(6);
        TOKEN_MAP.put(buildKey(clientId, code), authToken);
        return code;
    }

    @Override
    public OAuthToken consumeAuthorizationCode(String clientId, String code) {
        return TOKEN_MAP.remove(buildKey(clientId, code));
    }

    private final String buildKey(String clientId, String code) {
        return clientId + ":" + code;
    }
}
