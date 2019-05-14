package testBeanLifeCycle;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by iQiwen on 2019/5/14.
 */
public class AppTest {

    @Test
    public void testBeanLifeCycle() {

        System.out.println("Spring容器初始化");
        System.out.println("=====================================");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("testBeanLifeCycle_spring-beans.xml");

        System.out.println("Spring容器初始化完毕");
        System.out.println("=====================================");

        System.out.println("从容器中获取Bean");

        LifeCycleService service = context.getBean("lifeCycleService", LifeCycleService.class);

        System.out.println("lifeCycle Name=" + service.getName());
        System.out.println("=====================================");

        context.close();

        System.out.println("Spring容器关闭");

    }
}
