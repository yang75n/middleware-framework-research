import com.alibaba.dubbo.config.annotation.Service;


//Dubbo的Service注解
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String defaultMethod(String str) {
        System.out.println("called on dubbo-provider");
        return "hello," + str;
    }
}
