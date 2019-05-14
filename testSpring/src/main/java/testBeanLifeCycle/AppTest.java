package testBeanLifeCycle;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring在初始化容器时，会先解析和加载所有的Bean Class，
 * 如果符合要求则通过Class生成BeanDefinition，存入BeanFactory中，
 * 在加载完所有Bean Class后，开始有序的通过BeanDefinition实例化Bean。
 * <p/>
 * <p/>
 * Spring Bean的生命周期是这样纸的：
 * <p/>
 * 1.Bean容器找到配置文件中Spring Bean的定义。
 * 2.Bean容器利用Java Reflection API创建一个Bean的实例。
 * 3.如果涉及到一些属性值 利用set方法设置一些属性值。
 * 4.如果Bean实现了BeanNameAware接口，调用setBeanName()方法，传入Bean的名字。
 * 5.如果Bean实现了BeanClassLoaderAware接口，调用setBeanClassLoader()方法，传入ClassLoader对象的实例。
 * 6.如果Bean实现了BeanFactoryAware接口，调用setBeanClassLoader()方法，传入ClassLoader对象的实例。
 * 7.与上面的类似，如果实现了其他*Aware接口，就调用相应的方法。
 * 8.如果有和加载这个Bean的Spring容器相关的BeanPostProcessor对象，执行postProcessBeforeInitialization()方法
 * 9.如果Bean实现了InitializingBean接口，执行afterPropertiesSet()方法。
 * 10.如果Bean在配置文件中的定义包含init-method属性，执行指定的方法。
 * 11.如果有和加载这个Bean的Spring容器相关的BeanPostProcessor对象，执行postProcessAfterInitialization()方法
 * 12.当要销毁Bean的时候，如果Bean实现了DisposableBean接口，执行destroy()方法。
 * 13.当要销毁Bean的时候，如果Bean在配置文件中的定义包含destroy-method属性，执行指定的方法。
 * <p/>
 * Spring容器关闭过程
 * 1. 调用DisposableBean的destroy();
 * 2. 调用定制的destroy-method方法;
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
