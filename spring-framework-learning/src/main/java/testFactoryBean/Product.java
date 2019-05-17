package testFactoryBean;

/**
 * Created by iQiwen on 2019/5/14.
 */
public class Product {
    //@Value("你好")//此时无效
    private String id;
    //@Value("世界")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
