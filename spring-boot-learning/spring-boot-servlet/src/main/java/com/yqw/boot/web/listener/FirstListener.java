package com.yqw.boot.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * springBoot整合Listener
 * <p/>
 * <listener>
 * <listener-class>com.bjsxt.listener.FirstListener</listener-class>
 * </listener>
 */
@WebListener
/**
 * 实现那个监听器接口取决于我们要监听什么东西
 */
public class FirstListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("contextDestroyed.");


    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("contextInitialized Listener...init......");

    }

}
