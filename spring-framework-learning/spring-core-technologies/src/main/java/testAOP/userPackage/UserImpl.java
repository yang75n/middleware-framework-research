package testAOP.userPackage;

import java.util.HashMap;
import java.util.Map;

public class UserImpl implements IUser {

    private static Map map = new HashMap();

    private static void init() {
        String[] list = {"Lucy", "Tom", "小明", "Smith", "Hello"};
        map.clear();
        for (String s : list) {
            map.put(s, s + "-init");
        }

    }

    public void addUser(String username) {
        init();
        map.put(username, username + "-add");
        System.out.println("--------------【addUser】: " + username + " --------------");
        System.out.println("【The new List:" + map + "】");
    }

    public String findUser(String username) {
        // init();
        String password = "没查到该用户";
        if (map.containsKey(username)) {
            password = map.get(username).toString();
        }
        System.out.println("-----------------【findUser】-----------------");
        System.out.println("-----------------Username:" + username + "-----------------");
        System.out.println("-----------------【Result】:" + password + "------------------");
        return password;

    }

    public void findAll() {
        init();
        System.out.println("---------------【findAll】: " + map + " ------------------");
    }
}