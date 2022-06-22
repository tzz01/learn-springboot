package aop.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    RedisTemplate<String, Serializable> myRedisTemplate;

    public void saveValue(String key, Object value, long time) {
        myRedisTemplate.opsForValue().set(key, (Serializable) value, time, TimeUnit.MINUTES);
    }

    public Boolean containValue(String key) {
        return myRedisTemplate.hasKey(key);
    }
    public Object getValue(String key) {
        return myRedisTemplate.opsForValue().get(key);
    }

    public Boolean update(String key) {
        return myRedisTemplate.expire(key, 15, TimeUnit.MINUTES);
    }

    public Boolean delete(String key) {
        return myRedisTemplate.delete(key);
    }

}
