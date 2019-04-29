package testAOP;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import testAOP.userPackage.IUser;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUser user = (IUser) factory.getBean("user");
        System.out.println("user=" + user);
        user.findAll();

        user.addUser("yangqiwen");

        user.findUser("yangqiwen");

    }
}
