package testIOC.service;

import org.springframework.stereotype.Service;
import testIOC.repository.ProductRepository;
import testIOC.ThirdLib;

/**
 * Created by iQiwen on 2019/5/13.
 */

@Service("productService")
public class ProductService {

    private final ProductRepository productRepository;
    private final ThirdLib thirdLib;

    /**
     * 构造函数注入,有且只有一个构造函数时，@Autowired可以不写
     * 推荐使用构造函数的方式.
     * ，原因是 @Autowired一定要等本类构造完成后，
     * 才能从外部引用设置进来。所以@Autowired的注入时间一定会晚于构造函数的执行时间。
     * 但在初始化变量的时候就使用了还没注入的bean，所以导致了NPE。
     * 若果在初始化其它变量时不使用这个要注入的bean，
     * 而是在以后的方法调用的时候去赋值，是可以使用这个bean的，
     * 因为那时类已初始化好，即已注入好了。
     * <p/>
     * <p/>
     * <p/>
     * If a class, which is configured as a Spring bean,
     * has only one constructor, the Autowired annotation can be omitted
     * and Spring will use that constructor and
     * inject all necessary dependencies.
     */
    // @Autowired
    public ProductService(ProductRepository productRepository, ThirdLib thirdLib) {
        System.out.println("ProductService 构造函数");
        this.productRepository = productRepository;
        this.thirdLib = thirdLib;
    }


    @Override
    public String toString() {
        return "ProductService{" +
                "productRepository=" + productRepository +
                ", thirdLib=" + thirdLib +
                '}';
    }
}
