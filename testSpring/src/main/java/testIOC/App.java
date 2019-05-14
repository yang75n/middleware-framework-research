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

    }
}
