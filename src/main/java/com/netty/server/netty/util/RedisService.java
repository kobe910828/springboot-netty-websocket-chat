package com.netty.server.netty.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xin.huang
 * @version v1.0
 * @date 2018/6/21 14:17
 */
@Service("redisService")
public class RedisService {
    @Autowired //操作字符串的template，StringRedisTemplate是RedisTemplate的一个子集
    private StringRedisTemplate stringRedisTemplate;

    @Autowired  // RedisTemplate，可以进行所有的操作
    private RedisTemplate<Object,Object> redisTemplate;

    public void set(String key, String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void del(String key){
        stringRedisTemplate.delete(key);
    }
}
