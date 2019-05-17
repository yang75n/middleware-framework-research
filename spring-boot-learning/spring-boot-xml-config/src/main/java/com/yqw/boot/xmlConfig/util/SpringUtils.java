package com.yqw.boot.xmlConfig.util;

import com.yqw.boot.xmlConfig.service.HelloService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取ApplicatonContext
 * Created by iQiwen on 2019/5/17.
 */
public class SpringUtils implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("SpringUtils->setApplicationContext applicationContext=" + applicationContext);

        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        System.out.println(helloService.getHelloMessage());
    }
}
