package testBeanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.*;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

/**
 * InitializingBean和DisposableBean这两个接口都只包含一个方法。通过实现InitializingBean接口的afterPropertiesSet()
 * 方法可以在Bean属性值设置好之后做一些操作，
 * 实现DisposableBean接口的destroy()方法可以在销毁Bean之前做一些操作。
 * <p/>
 * 有些时候我们需要在Bean的初始化中使用Spring框架自身的一些对象来执行一些操作，比如获取ServletContext的一些参数，获取ApplicaitionContext中的BeanDefinition的名字，获取Bean在容器中的名字等等。为了让Bean可以获取到框架自身的一些对象，Spring提供了一组名为*Aware的接口。
 * 这些接口均继承于org.springframework.beans.factory.Aware标记接口，并提供一个将由Bean实现的set*方法,Spring通过基于setter的依赖注入方式使相应的对象可以被Bean使用。
 * 网上说，这些接口是利用观察者模式实现的，类似于servlet listeners，目前还不明白，不过这也不在本文的讨论范围内。
 * 介绍一些重要的Aware接口：
 * <p/>
 * ApplicationContextAware: 获得ApplicationContext对象,可以用来获取所有Bean definition的名字。
 * BeanFactoryAware:获得BeanFactory对象，可以用来检测Bean的作用域。
 * BeanNameAware:获得Bean在配置文件中定义的名字。
 * ResourceLoaderAware:获得ResourceLoader对象，可以获得classpath中某个文件。
 * ServletContextAware:在一个MVC应用中可以获取ServletContext对象，可以读取context中的参数。
 * ServletConfigAware在一个MVC应用中可以获取ServletConfig对象，可以读取config中的参数。
 */
public class LifeCycleService implements InitializingBean, DisposableBean, ApplicationContextAware,
        ApplicationEventPublisherAware, BeanClassLoaderAware, BeanFactoryAware,
        BeanNameAware, EnvironmentAware, ImportAware, ResourceLoaderAware {

    private String name;

    public String getName() {
        return name;
    }

    public LifeCycleService setName(String name) {
        System.out.println("LifeCycleService中利用set方法设置属性值");
        this.name = name;
        return this;
    }

    public LifeCycleService() {
        System.out.println("调用LifeCycleService无参构造函数");
    }

    /**
     * 实现自InitializingBean接口中的函数
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行InitializingBean接口的afterPropertiesSet方法");

    }

    /**
     * 实现自DisposableBean接口中的函数
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("执行DisposableBean接口的destroy方法");
    }


    /**
     * 通过<bean>的destroy-method属性指定的销毁方法
     *
     * @throws Exception
     */
    public void destroyMethod() throws Exception {
        System.out.println("执行配置的destroy-method");
    }

    /**
     * 通过<bean>的init-method属性指定的初始化方法
     *
     * @throws Exception
     */
    public void initMethod() throws Exception {
        System.out.println("执行配置的init-method");
    }

    /**
     * 除了xml配置的方式，Spring也支持用@PostConstruct和 @PreDestroy注解来指定init和destroy方法。这两个注解均在javax.annotation包中。
     * 为了注解可以生效，需要在配置文件中定义org.springframework.context.annotation.CommonAnnotationBeanPostProcessor或context:annotation-config
     */
    @PostConstruct
    public void initPostConstruct() {
        System.out.println("执行PostConstruct注解标注的方法");
    }

    /**
     * 同上
     */
    @PreDestroy
    public void preDestroy() {
        System.out.println("执行preDestroy注解标注的方法");
    }

    /**
     * ApplicationContextAware: 获得ApplicationContext对象,可以用来获取所有Bean definition的名字。
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("执行setApplicationContext:: Bean Definition Names="
                + Arrays.toString(applicationContext.getBeanDefinitionNames()));

    }

    /**
     * BeanClassLoaderAware
     *
     * @param classLoader
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("执行setBeanClassLoader,ClassLoader Name = " + classLoader.getClass().getName());
    }


    /**
     * BeanFactoryAware:获得BeanFactory对象，可以用来检测Bean的作用域。
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("执行setBeanFactory,setBeanFactory:: giraffe bean singleton=" + beanFactory.isSingleton("lifeCycleService"));
    }

    /**
     * BeanNameAware:获得Bean在配置文件中定义的名字。
     *
     * @param s
     */
    @Override
    public void setBeanName(String s) {
        System.out.println("执行setBeanName:: Bean Name defined in context="
                + s);
    }


    /**
     * ApplicationEventPublisherAware
     *
     * @param applicationEventPublisher
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("执行setApplicationEventPublisher");
    }

    /**
     * EnvironmentAware
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("执行setEnvironment");
    }

    /**
     * ResourceLoaderAware:获得ResourceLoader对象，可以获得classpath中某个文件。
     *
     * @param resourceLoader
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

        Resource resource = resourceLoader.getResource("classpath:testBeanLifeCycle_spring-beans.xml");
        System.out.println("执行setResourceLoader:: Resource File Name="
                + resource.getFilename());

    }

    /**
     * ImportAware
     *
     * @param annotationMetadata
     */
    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        System.out.println("执行setImportMetadata");
    }
}
