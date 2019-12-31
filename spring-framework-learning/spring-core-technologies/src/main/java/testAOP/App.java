package testAOP;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import testAOP.userPackage.IUser;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        BeanFactory factory = new ClassPathXmlApplicationContext("testAOP_applicationContext.xml");
        IUser user = (IUser) factory.getBean("user");
        System.out.println("user=" + user);

        user.addUser("yangqiwen");

        user.findUser("yangqiwen");

        user.findAll();

        System.out.println("user=" + user);


    }
}
