package com.yqw.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static Configuration cfg = null;
    private static ServiceRegistry registry = null;
    private static SessionFactory factory = null;

    static {
        //1、读取hibernate.cfg.xml配置文件
        cfg = new Configuration().configure();
        //2、从4.0开始有这一行代码；创建服务注册
        registry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        //3、创建SessionFactory对象,SessionFactory是个重量级的对象，在一个应用里面只需要一个即可，是进程级别的，可以在集群中使用。
        //操作完后一般不会释放
        factory = cfg.buildSessionFactory(registry);
    }

    /**
     * 可以用ThreadLocal来设置线程级别Session对象
     */
    public static Session getSession() {
        //4、创建Session对象,5、通过Session对象可以实现增删改查；6、关闭Session和SessionFactory
        return factory.openSession();
    }
}
