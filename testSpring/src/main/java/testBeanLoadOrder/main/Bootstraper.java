package testBeanLoadOrder.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author dell-7359
 * @create 2018-02-15 18:31
 */
@Import(A.class)
@Configuration
public class Bootstraper {
}
