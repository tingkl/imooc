package com.mix.miaosha.redis;

import com.mix.miaosha.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            String realKey = prefix.getPrefix() + key;
            jedis = jedisPool.getResource();
            String str = jedis.get(realKey);
            T t = ConvertUtil.stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    public Boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            String realKey = prefix.getPrefix() + key;
            jedis = jedisPool.getResource();
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = ConvertUtil.beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expiredSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            String realKey = prefix.getPrefix() + key;
            jedis = jedisPool.getResource();
            long ret = jedis.del(realKey);
            return ret > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            String realKey = prefix.getPrefix() + key;
            jedis = jedisPool.getResource();
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            String realKey = prefix.getPrefix() + key;
            jedis = jedisPool.getResource();
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }



    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
