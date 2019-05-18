package testIOC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import testIOC.service.ProductService;

/**
 * Created by iQiwen on 2019/5/13.
 */
@Lazy(false)
@Controller
public class ProductController {

    private ProductService productService;

    /*
     * set方法注入
     *
     *
     */
    @Qualifier("productService")
    @Autowired
    public void setProductService(ProductService productService) {
        System.out.println("ProductController->setProductService");
        this.productService = productService;
    }

    @Override
    public String toString() {
        return "ProductController{" +
                "productService=" + productService +
                '}';
    }
}
