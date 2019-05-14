package testIOC;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import testIOC.domain.Product;

import javax.sql.DataSource;

/**
 * 配置类可以是Spring容器的起始配置类，也可以是通过@ComponentScan扫描得到的类，
 * 也可以是通过@Import引入的类。
 * 如果这个类上含有@Configuration，@Component，@ComponentScan，@Import，@ImportResource注解中的一个，或者内部含有@Bean标识的方法，那么这个类就是一个配置类，Spring就会按照一定流程去解析这个类上的信息。
 * 在解析的第一步会校验当前类是否已经被解析过了，
 * 如果是，那么需要按照一定的规则处理
 * （@ComponentScan得到的Bean能覆盖@Import得到的Bean， @Bean定义的优先级最高）。
 */
@Import(com.mchange.v2.c3p0.ComboPooledDataSource.class)
@Configuration
@ComponentScan(basePackages = {})
public class Config {


    /**
     * 引用第三方jar中的类
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        System.out.println("Config->dataSource");
        return new DruidDataSource();
    }

    /**
     * 引用自定义类
     *
     * @return
     */
    @Bean
    public ThirdLib thirdLib() {
        System.out.println("Config->thirdLib");
        return new ThirdLib();
    }


    /**
     * 测试@Bean优先级
     *
     * @return
     */
    @Bean
    public Product product1() {
        System.out.println("Config->product1");
        return new Product("Config", "ConfigName");
    }

}
