package com.yqw.boot.custom.starter.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 这是一个简单的属性值对象，那么相当于写死的字段就是SpringBoot为我们自动配置的配置，
 * 那么我们很多时候可以自己在application.properties中修改某些配置就是这样的道理，
 * 我们不设置就是默认的，设置了就是我们设置的属性。
 * <p/>
 * Configuration properties for Hello.
 * Created by iQiwen on 2019/5/21.
 */
@ConfigurationProperties(prefix = "spring.yqw.hello")
//@Component //如果这里添加了注解那么在自动配置类的时候就不用添加@enableConfigurationProperties(HelloProperties.class)注解.
public class HelloProperties {
    /**
     * 现在我们在配置文件写spring.yqw.hello=world,如果那么默认为default.
     */
    private String name = "default";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
