package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



@Component("redisClient")
public class RedisClient {
    @Autowired
    private JedisPool jedisPool;

    //连接设置redis中的键和值
    public void set(String key, String value) throws Exception {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            //返还到连接池
            jedis.close();
        }

    }
// 获取资源池中的键
    public String get(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
// 返回到连接池
            jedis.close();
        }
    }
}