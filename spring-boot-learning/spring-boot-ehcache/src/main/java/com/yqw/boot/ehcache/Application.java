package com.yqw.boot.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 没有controller层，通过测试类进行service层单元测试
 * Ehcache是一个纯Java的进程内缓存框架，具有快速、精干等特点。
 * <p/>
 * <p/>
 * 在Spring Boot中通过@EnableCaching注解自动化配置合适的缓存管理器（CacheManager），Spring Boot根据下面的顺序去侦测缓存提供者：
 * <p/>
 * Generic
 * JCache (JSR-107)
 * EhCache 2.x
 * Hazelcast
 * Infinispan
 * Redis
 * Guava
 * Simple
 * <p/>
 * 除了按顺序侦测外，我们也可以通过配置属性spring.cache.type来强制指定。我们可以通过debug调试查看cacheManager对象的实例来判断当前使用了什么缓存。
 * <p/>
 */
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
