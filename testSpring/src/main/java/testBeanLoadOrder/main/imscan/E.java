package testBeanLoadOrder.main.imscan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dell-7359
 * @create 2018-02-15 18:06
 */
@ComponentScan
@Configuration("e")
public class E {

    @Bean
    public G g() {
        return new G();
    }
}
