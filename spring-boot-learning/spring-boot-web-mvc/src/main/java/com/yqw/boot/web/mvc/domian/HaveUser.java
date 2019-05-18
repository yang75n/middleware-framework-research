package com.yqw.boot.web.mvc.domian;

/**
 * Created by Qiwen on 2019/5/19.
 */
public class HaveUser {
    private  User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "HaveUser{" +
                "user=" + user +
                '}';
    }
}
