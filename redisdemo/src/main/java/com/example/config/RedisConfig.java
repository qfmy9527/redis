package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration   //注解相当于<beans>
public class RedisConfig extends CachingConfigurerSupport {
    Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    // 获取服务器地址
    @Value("spring.redis.host")
    private String host;
    //获取服务器端口号
    @Value("${spring.redis.port}")
    private int port;
    //获取最大超时时间
    @Value("${spring.redis.timeout}")
    private int timeout;
    // 最大连接空闲连接
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    //最大阻塞时间
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;
    //获取连接密码
    @Value("${spring.redis.password}")
    private String password;


    @Bean
    public JedisPool reisPoolFarctroy() {
        logger.info("JedisPool 注入成功！！");
        logger.info("连接信息:" + host + "端口：" + port);
        JedisPoolConfig pool = new JedisPoolConfig();
        pool.setMaxWaitMillis(maxWaitMillis);
        pool.setMaxIdle(maxIdle);
        JedisPool JedisPool = new JedisPool(pool, host, port, timeout, password);
        return JedisPool;
    }

}
