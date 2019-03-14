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
    FileName: RedisAuthorizationCodeServicesImpl.java
    Date: 2019/3/14
    Author: lq
*/
package com.lq186.oauth2.service.redis;

import com.lq186.common.bean.RedisObject;
import com.lq186.common.log.Log;
import com.lq186.common.springboot.redis.RedisUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.HashMap;
import java.util.Map;

public class RedisAuthorizationCodeServicesImpl extends RandomValueAuthorizationCodeServices {

    private static final Log log = Log.getLog(RedisAuthorizationCodeServicesImpl.class);

    private final static String REDIS_PREFIX = "OAUTH2-CODE:";

    private final static Map<String, Object> CODE_MAP = new HashMap<>();

    @Override
    protected void store(String code, OAuth2Authentication oAuth2Authentication) {
        String redisKey = buildRedisKey(code);
        log.debugf("存储CODE对应的授权对象, CODE -> %s", code);
        if (RedisUtils.isEnabled()) {
            RedisUtils.setValue(redisKey, oAuth2Authentication);
        } else {
            CODE_MAP.put(redisKey, oAuth2Authentication);
        }
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        String redisKey = buildRedisKey(code);
        Object valueObject;
        if (RedisUtils.isEnabled()) {
            valueObject = RedisUtils.getValue(redisKey);
            try {
                RedisUtils.deleteByKey(redisKey);
            } catch (Exception e) {
                log.errorf("删除缓存数据异常, REDIS KEY -> %s", e, redisKey);
            }
        } else {
            valueObject = CODE_MAP.get(redisKey);
        }
        if (null == valueObject || valueObject instanceof RedisObject) {
            log.debugf("获取CODE对应的授权对象失败, CODE -> %s", code);
            return null;
        }
        log.debugf("成功获取CODE对应的授权对象, CODE -> %s", code);
        return (OAuth2Authentication) valueObject;
    }

    private static final String buildRedisKey(String code) {
        return REDIS_PREFIX + code;
    }
}
