package com.yqw.boot.web.mvc.domian;

import java.util.List;

/**
 * Created by Qiwen on 2019/5/19.
 */
public class ListUser {
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ListUser{" +
                "users=" + users +
                '}';
    }
}
