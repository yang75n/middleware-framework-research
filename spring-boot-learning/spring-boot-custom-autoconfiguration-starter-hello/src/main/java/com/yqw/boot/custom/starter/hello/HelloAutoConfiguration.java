package com.yqw.boot.custom.starter.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by iQiwen on 2019/5/21.
 */
@Configuration//配置类
@ConditionalOnClass(HelloService.class) //这里就是前面说的，这个注解读入我们的配置对象类
@EnableConfigurationProperties(HelloProperties.class) //当类路径存在这个类时才会加载这个配置类，否则跳过,这个很有用比如不同jar包间类依赖，依赖的类不存在直接跳过，不会报错
public class HelloAutoConfiguration {

    @Autowired
    private HelloProperties helloProperties;

    //这个配置就是SpringBoot可以优先使用自定义Bean的核心所在，如果没有我们的自定义Bean那么才会自动配置一个新的Bean
    @ConditionalOnMissingBean(HelloService.class)
    @Bean
    public HelloService helloService() {
        System.out.println("@Bean HelloAutoConfiguration->helloService()");
        HelloService helloService = new HelloService();
        helloService.setName(helloProperties.getName());
        return helloService;
    }
}
