package hello;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Application {


    @Bean
    MessageService mockMessageService() {
        System.out.println("mockMessageService");
        return new MessageService() {
            public String getMessage() {
              return "Hello World!";
            }
        };
    }

  public static void main(String[] args) {
      LogFactory.getLog(Application.class).info("测试log");
      ApplicationContext context =
          new AnnotationConfigApplicationContext(Application.class);
      System.out.println("after init ApplicationContext");
      MessagePrinter printer = context.getBean(MessagePrinter.class);
      printer.printMessage();
  }
}