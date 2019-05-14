package testBeanLoadOrder.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import testBeanLoadOrder.main.imscan.E;

/**
 * 配置类
 *
 * @author dell-7359
 * @create 2018-02-15 17:57
 */
@Configuration("a")
@Import(E.class)
@ComponentScan("testBeanLoadOrder.main.scan")
public class A extends H {

    @Bean
    public C c() {
        return new C();
    }

    @Component("b")
    public class B {

    }

}
