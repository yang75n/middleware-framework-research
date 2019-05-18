package testBeanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 那些*Aware接口是针对某个实现这些接口的Bean定制初始化的过程，
 * Spring同样可以针对容器中的所有Bean，或者某些Bean定制初始化过程，
 * 只需提供一个实现BeanPostProcessor接口的类即可。 该接口中包含两个方法，
 * postProcessBeforeInitialization和postProcessAfterInitialization。
 * postProcessBeforeInitialization方法会在容器中的Bean初始化之前执行，
 * postProcessAfterInitialization方法在容器中的Bean初始化之后执行。
 */
public class CustomerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("执行BeanPostProcessor的postProcessBeforeInitialization方法,beanName=" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("执行BeanPostProcessor的postProcessAfterInitialization方法,beanName=" + beanName);
        return bean;
    }


}
