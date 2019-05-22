package com.yqw.cloud.discovery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Service Discovery服务器
 * <p/>
 * Created by iQiwen on 2019/5/15.
 *
 * @since 1.0.0
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {
    private static final Log logger = LogFactory.getLog(Application.class);

    public static void main(String[] args) {
        if (logger.isInfoEnabled()) {
            logger.info("开启服务发现");
        }

        SpringApplication.run(Application.class, args);
    }
}
