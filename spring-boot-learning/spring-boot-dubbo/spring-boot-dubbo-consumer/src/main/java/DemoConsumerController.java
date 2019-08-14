import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoConsumerController {
    @Reference
    private DemoService demoService;

    @RequestMapping("/hello")
    public String sayHello(@RequestParam String name) {
        return demoService.defaultMethod(name);
    }

}
