package com.yqw.boot.xmlConfig;

import com.yqw.boot.xmlConfig.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.Collections;

/**
 * 没有@SpringBootApplication注解,采用xml方式配置bean
 * <p/>
 * Created by iQiwen on 2019/5/17.
 */
public class Application implements CommandLineRunner {


    private static final String CONTEXT_XML = "classpath:/META-INF/application-context.xml";

    @Autowired
    private HelloService helloService;

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication();
        /* Set additional sources that will be used to create an ApplicationContext.
         A source can be: a class name, package name, or an XML resource location.*/
        application.setSources(Collections.singleton(CONTEXT_XML));
        application.run(args);


    }


    /**
     * SpringBoot中CommandLineRunner的作用
     * 平常开发中有可能需要实现在项目启动后执行的功能，
     * SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，
     * 实现功能的代码放在实现的run方法中
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        System.out.println("args=" + args);
        System.out.println(helloService.getHelloMessage());
    }
}
