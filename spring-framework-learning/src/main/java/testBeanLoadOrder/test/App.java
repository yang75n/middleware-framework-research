package testBeanLoadOrder.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import testBeanLoadOrder.main.A;

import java.util.Arrays;

/**
 * https://blog.csdn.net/qq_27529917/article/details/79329809
 * <p/>
 * Spring在初始化容器时，会先解析和加载所有的Bean Class，如果符合要求则通过Class生成BeanDefinition，存入BeanFactory中，在加载完所有Bean Class后，开始有序的通过BeanDefinition实例化Bean。
 * 我们先看加载Bean Class过程，零配置下Spring Bean的加载起始于ConfigurationClassPostProcessor的postProcessBeanDefinitionRegistry（BeanDefinitionRegistry）方法，我总结了下其加载解析Bean Class的流程：
 * <p/>
 * 配置类可以是Spring容器的起始配置类，也可以是通过@ComponentScan扫描得到的类，也可以是通过@Import引入的类。如果这个类上含有@Configuration，@Component，@ComponentScan，@Import，@ImportResource注解中的一个，或者内部含有@Bean标识的方法，那么这个类就是一个配置类，Spring就会按照一定流程去解析这个类上的信息。
 * 在解析的第一步会校验当前类是否已经被解析过了，如果是，那么需要按照一定的规则处理（@ComponentScan得到的Bean能覆盖@Import得到的Bean，@Bean定义的优先级最高）。
 * 如果未解析过，那么开始解析：
 * <p/>
 * 解析内部类，查看内部类是否应该被定义成一个Bean，如果是，递归解析。
 * 解析@PropertySource，也就是解析被引入的Properties文件。
 * 解析配置类上是否有@ComponentScan注解，如果有则执行扫描动作，通过扫描得到的Bean Class会被立即解析成BeanDefinition，添加进beanDefinitionNames属性中。之后查看扫描到的Bean Class是否是一个配置类（大部分情况是，因为标识@Component注解），如果是则递归解析这个Bean Class。
 * 解析@Import引入的类，如果这个类是一个配置类，则递归解析。
 * 解析@Bean标识的方法，此种形式定义的Bean Class不会被递归解析
 * 解析父类上的@ComponentScan，@Import，@Bean，父类不会被再次实例化，因为其子类能够做父类的工作，不需要额外的Bean了。
 * <p/>
 * 在1，3，4，6中都有递归操作，也就是在解析一个Bean Class A时，发现其上能够获取到其他Bean Class B信息，此时会递归的解析Bean Class B，在解析完Bean Class B后再接着解析Bean Class A，可能在解析B时能够获取到C，那么也会先解析C再解析B，就这样不断的递归解析。
 * 在第3步中，通过@ComponentScan扫描直接得到的Bean Class会被立即加载入beanDefinitionNames中，但@Import和@Bean形式定义的Bean Class则不会，也就是说正常情况下面@ComponentScan直接得到的Bean其实例化时机比其他两种形式的要早。
 * 通过@Bean和@Import形式定义的Bean Class不会立即加载，他们会被放入一个ConfigurationClass类中，然后按照解析的顺序有序排列，就是图片上的 “将配置类有序排列”。一个ConfigurationClass代表一个配置类，这个类可能是被@ComponentScan扫描到的，则此类已经被加载过了；也可能是被@Import引入的，则此类还未被加载；此类中可能含有@Bean标识的方法。
 * Spring在解析完了所有Bean Class后，开始加载ConfigurationClass。如果这个ConfigurationClass是被Import的，也就是说在加载@ComponentScan时其未被加载，那么此时加载ConfigurationClass代表的Bean Class。然后加载ConfigurationClass内的@Bean方法。
 * 顺序总结：@ComponentScan  > @Import  > @Bean
 */
public class App {

    /**
     * A是配置类的入口，通过A能直接或间接的引入一个模块。
     * 此时启动Spring容器，将A引入容器内。
     * 如果A是通过@ComponentScan扫描到的，那么此时的加载顺序是：
     * A > D > F > B > E > G > C
     * 如果A是通过@Import形式引入的，那么此时的加载顺讯是：
     * D > F > B > E > G > A > C
     * 当然以上仅仅代表着加载Bean Class的顺序，实际实例化Bean的顺序和加载顺序大体相同，但还是会有一些差别。
     * Spring在通过getBean(beanName)形式实例化Bean时，会通过BeanDefinition去生成Bean对象。在这个过程中，如果BeanDefinition的DependsOn不为空，从字面理解就是依赖某个什么，其值一般是某个或多个beanName，也就是说依赖于其他Bean，此时Spring会将DependsOn指定的这些名称的Bean先实例化，也就是先调用getBean(dependsOn)方法。我们可以通过在Bean Class或者@Bean的方法上标识**@DependsOn**注解，来指定当前Bean实例化时需要触发哪些Bean的提前实例化。
     * 当一个Bean A内部通过@Autowired或者@Resource注入Bean B，那么在实例化A时会触发B的提前实例化，此时会注册A>B的dependsOn依赖关系，实质和@DependsOn一样，这个是Spring自动为我们处理好的。
     */
    @Test
    public void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(A.class);
        // ApplicationContext context = new AnnotationConfigApplicationContext(Bootstraper.class);
        System.out.println(context.getBeanDefinitionCount());
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }
}
