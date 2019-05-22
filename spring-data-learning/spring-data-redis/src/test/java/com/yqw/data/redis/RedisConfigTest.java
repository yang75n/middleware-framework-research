package com.yqw.data.redis;

import com.yqw.data.redis.config.RedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 通过Java配置方式配置Redis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
public class RedisConfigTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加一个字符串
     */
    @Test
    public void testSet() {
        this.redisTemplate.opsForValue().set("key", "杨其文");
    }

    /**
     * 获取一个字符串
     */
    @Test
    public void testGet() {
        String value = (String) this.redisTemplate.opsForValue().get("key");
        System.out.println(value);
    }
}
