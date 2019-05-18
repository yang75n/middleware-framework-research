package testIOC.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import testIOC.domain.Product;

@Scope(BeanDefinition.SCOPE_SINGLETON)//默认就是单例
@Repository
public class ProductRepository {
    /*
     * 字段注入
     */
    //@Qualifier("product")
    @Autowired//@Resource
    private Product product;


    //  @Autowired
    //    public ProductRepository(Product product) {
    //        System.out.println("ProductRepository  构造函数");
    //        this.product = product;
    //    }



    @Override
    public String toString() {
        return "ProductRepository{" +
                "product=" + product +
                '}';
    }
}
