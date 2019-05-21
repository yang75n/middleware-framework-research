package com.yqw.data.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 完成对Redis的整合的一些配置
 */
@Configuration
public class RedisConfig {

    /**
     * 1.创建JedisPoolConfig对象。在该对象中完成一些链接池配置
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        System.out.println("create jedisPoolConfig...");

        JedisPoolConfig config = new JedisPoolConfig();
        //最大空闲数
        config.setMaxIdle(10);
        //最小空闲数
        config.setMinIdle(5);
        //最大链接数
        config.setMaxTotal(20);

        System.out.println("return config");

        return config;
    }

    /**
     * 2.创建JedisConnectionFactory：配置redis链接信息
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config) {
        System.out.println("create jedisConnectionFactory...");

        JedisConnectionFactory factory = new JedisConnectionFactory();
        //关联链接池的配置对象
        factory.setPoolConfig(config);
        //配置链接Redis的信息
        //主机地址
        factory.setHostName("192.168.70.128");
        //端口
        factory.setPort(6379);
        System.out.println("return factory");

        return factory;
    }

    /**
     * 3.创建RedisTemplate:用于执行Redis操作的方法
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory) {
        System.out.println("create RedisTemplate...");
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        //关联
        template.setConnectionFactory(factory);

        //为key设置序列化器
        template.setKeySerializer(new StringRedisSerializer());
        //为value设置序列化器
        template.setValueSerializer(new StringRedisSerializer());


        return template;
    }
}
