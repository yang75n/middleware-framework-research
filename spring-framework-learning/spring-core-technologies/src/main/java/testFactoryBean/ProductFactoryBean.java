package testFactoryBean;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by iQiwen on 2019/5/13.
 */

public class ProductFactoryBean implements FactoryBean<Product> {
    @Override
    public Product getObject() throws Exception {
        System.out.println("ProductFactory getObject,new Prduct");
        return new Product();
    }

    @Override
    public Class<?> getObjectType() {
        return Product.class;
    }
}
