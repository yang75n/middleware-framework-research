package hello;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SpringBootApplication
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home(int i) {
        return "<h1>Hello World!<h1>" + ++i;
    }


    @RequestMapping("/hello")
    String hello() {
        System.out.println("access /hello");
        return "hello";
    }

    @RequestMapping("/model")
    ModelAndView model() {
        System.out.println("access /model");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("name", "Yang Qiwen");
        ModelAndView modelAndView=new  ModelAndView("model", modelMap);
        return  modelAndView;
    }


    @RequestMapping("/list")
    public String userList(Model model) throws Exception {
        model.addAttribute("hello","Hello, Spring Boot!");
        return "/list";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}