package com.example.jedisdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@EnableCaching
@SpringBootTest
public class JedisdemoApplicationTests {


    /**
     * 单机版redis构造器连接
     */
    @Test
    public void contextLoads() {
        // 连接Redis
        Jedis client = new Jedis("127.0.0.1", 6379);
        client.auth("123");

        // String 类型
        client.set("key1", "value1");
        System.out.println("-------------- String 类型 -------------");
        System.out.println(client.get("key1"));

        // Hash 类型
        client.hset("key2", "field1", "value2_1");
        client.hset("key2", "field2", "value2_2");

        System.out.println("-------------- Hash 类型 -------------");
        System.out.println(client.hget("key2", "field1"));
        System.out.println(client.hget("key2", "field2"));

        Map<String, String> map = client.hgetAll("key2");
        System.out.println(map.get("field1"));
        System.out.println(map.get("field2"));

        // List 类型
        client.lpush("key3", "value3_1", "value3_2");
        System.out.println("-------------- List 类型 -------------");
        System.out.println(client.lpop("key3"));
        System.out.println(client.lpop("key3"));

        // Set 类型
        client.sadd("key4", "value4_1", "value4_2");
        Set<String> set = client.smembers("key4");
        System.out.println("-------------- Set 类型 -------------");
        for (String val : set) {
            System.out.println(val);
        }

    }


    /**
     *  使用jedispool 连接redis
     */
    @Test
    public void utilRedis() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(8);
        // 最大空闲数
        poolConfig.setMaxIdle(8);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(500);
        Jedis jedis = null;
        //  其中的password是在redis desktop maneage 中连接的 auth
        JedisPool pool = new JedisPool(poolConfig, "localhost", 6379, 1000, "123");
//        jedis = pool.getResource();
//        jedis.set("1", "我");
//        System.out.println(jedis.get("1"));
    try {
        for (int i = 0; i < 5; i++) {
            jedis = pool.getResource();
            jedis.set("foo" + i, "bar" + i);
            System.out.println("第" + (i + 1) + "个连接, 得到的值为" + jedis.get("foo" + i));
            // 用完一定要释放连接
            jedis.close();
        }
    } finally {
        pool.close();
    }
    }
}
