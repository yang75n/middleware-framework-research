package testFactoryBean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * Spring中有两种类型的Bean，一种是普通Bean，
 * 另一种是工厂Bean，即FactoryBean。工厂Bean跟普通Bean不同，其返回的对象不是指定类的一个实例，
 * 其返回的是该工厂Bean的getObject方法所返回的对象。
 * <p/>
 * 由此可见，通过使用FactoryBean，我们可以得到不同类型的对象实例。
 * 这也就是我们在AOP中通过设置calss为ProxyFactoryBean可以返回不同类型的业务对象的原理。
 * 在实际应用中若能灵活使用FactoryBean，则可以给应用程序增加很多的魔幻功能。
 * <p/>
 * BeanFactory和FactoryBean的区别
 * <p/>
 * BeanFactory是接口，提供了OC容器最基本的形式，给具体的IOC容器的实现提供了规范，
 * <p/>
 * FactoryBean也是接口，为IOC容器中Bean的实现提供了更加灵活的方式，FactoryBean在IOC容器的基础上给Bean的实现加上了一个简单工厂模式和装饰模式,我们可以在getObject()方法中灵活配置。其实在Spring源码中有很多FactoryBean的实现类.
 */
public class App {
    @Test
    public void test() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProductFactoryBean.class);
        System.out.println("--------------");
        Product product = applicationContext.getBean("productFactoryBean", Product.class);
        System.out.println(product);
        /*
         * FactoryBean是一个接口，当在IOC容器中的Bean实现了FactoryBean后，
         * 通过getBean(String BeanName)获取到的Bean对象并不是FactoryBean的实现类对象，
         * 而是这个实现类中的getObject()方法返回的对象。要想获取FactoryBean的实现类，就要getBean(&BeanName)，
         * 在BeanName之前加上&。
         */
        ProductFactoryBean productFactoryBean = applicationContext.getBean("&productFactoryBean", ProductFactoryBean.class);
        System.out.println(productFactoryBean);
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));

    }
}
