package testIOC;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import testIOC.controller.ProductController;
import testIOC.domain.Product;
import testIOC.repository.ProductRepository;
import testIOC.service.ProductService;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * BeanFacotry是spring中比较原始的Factory。如XMLBeanFactory就是一种典型的BeanFactory。
 * 原始的BeanFactory无法支持spring的许多插件，如AOP功能、Web应用等。
 * ApplicationContext接口,它由BeanFactory接口派生而来，
 * ApplicationContext包含BeanFactory的所有功能，通常建议比BeanFactory优先
 */
public class App {


    @Test
    public void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        //System.out.println("applicationName=" + context.getApplicationName());
        System.out.println("-----------------------------");
        Product product = context.getBean("product", Product.class);
        System.out.println(product);
        System.out.println("-----------------------------");
        Product product1 = (Product) context.getBean("product1");
        System.out.println(product1);
        System.out.println("-----------------------------");
        Config config = context.getBean(Config.class);
        System.out.println("config=" + config);

        System.out.println("-----------------------------");
        ThirdLib thirdLib = context.getBean(ThirdLib.class);
        System.out.println("thirdLib=" + thirdLib);
        System.out.println("-----------------------------");
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        System.out.println("productRepository=" + productRepository);
        System.out.println("-----------------------------");
        ProductService productService = context.getBean("productService", ProductService.class);
        System.out.println(productService);
        System.out.println("-----------------------------");
        ProductController productController = context.getBean(ProductController.class);
        System.out.println("productController=" + productController);

        System.out.println("-----------------------------");
        //通过@Bean装载
        DataSource dataSource = context.getBean(DruidDataSource.class);
        System.out.println("dataSource=" + dataSource);


        System.out.println("-----------------------------");
        //@通过@Import装载
        ComboPooledDataSource comboPooledDataSource = context.getBean(ComboPooledDataSource.class);
        System.out.println("ComboPooledDataSource=" + comboPooledDataSource);

        System.out.println("所有Beans = " + Arrays.toString(context.getBeanDefinitionNames()));

    }
}
