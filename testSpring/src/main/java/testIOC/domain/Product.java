package testIOC.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by iQiwen on 2019/5/13.
 */
@Scope(value = BeanDefinition.SCOPE_SINGLETON)//默认就是单例
@Component("product")
public class Product {

    @Value("世界id")
    private String id;
    @Value("你好name")
    private String name;

    private Product() {
        System.out.println("Product构造函数");

    }


    public Product(String id, String name) {
        System.out.println("Product构造函数2");
    }


    public String getName() {
        return name;
    }

    @Value("你好setName")
    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
    }

    public String getId() {
        return id;
    }

    // @Value("世界setId")
    public void setId(String id) {
        System.out.println("setId");
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
