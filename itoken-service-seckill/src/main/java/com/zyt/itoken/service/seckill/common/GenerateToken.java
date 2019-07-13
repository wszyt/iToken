package com.zyt.itoken.service.seckill.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GenerateToken {

    @Autowired
    RedisUtil redisUtil;


    /**
     * 生成令牌
     * @param keyPrefix 令牌前缀
     * @param redisValue redis 存放的值
     * @return
     */
    public String createToken(String keyPrefix, String redisValue) {
        return createToken (keyPrefix, redisValue, null);
    }


    /**
     * 生成令牌
     * @param keyPrefix
     * @param redisValue
     * @param time 有效时间
     * @return
     */
    public String createToken(String keyPrefix, String redisValue, Long time) {
        if (StringUtils.isEmpty (redisValue)) {
            new Exception ("redisValue can not be null");
        }
        String token = keyPrefix + UUID.randomUUID ().toString ().replace ("-", "");
        redisUtil.setString (token, redisValue, time);
        return token;
    }


    public void createListToken(String keyPrefix, String redisKey, Long tokenQuantity) {
        List<String> listToken = getListToken(keyPrefix, tokenQuantity);
        redisUtil.setList(redisKey, listToken);
    }

    public List<String> getListToken(String keyPrefix, Long tokenQuantity) {
        List<String> listToken = new ArrayList<> ();
        for (int i = 0; i < tokenQuantity; i++) {
            String token = keyPrefix + UUID.randomUUID().toString().replace("-", "");
            listToken.add(token);
        }
        return listToken;

    }

    public String getListKeyToken(String key) {
        String value = redisUtil.getStringRedisTemplate().opsForList().leftPop(key);
        return value;
    }


    /**
     * 根据 token 获取 redis 中 value 值
     * @param token
     * @return
     */
    public String getToken(String token) {
        if (StringUtils.isEmpty (token)) {
            return null;
        }

        String value = redisUtil.getString (token);
        return value;
    }

    /**
     * 删除 token 对应 redis value 值
     * @param token
     * @return
     */
    public Boolean removeToken(String token) {
        if (StringUtils.isEmpty (token)) {
            return null;
        }

        return redisUtil.delKey (token);
    }


}
