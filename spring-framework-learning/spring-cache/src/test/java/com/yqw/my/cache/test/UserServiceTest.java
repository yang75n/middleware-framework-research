package com.yqw.my.cache.test;

import com.yqw.my.cache.UserService;
import org.junit.Test;

/**
 * Created by iQiwen on 2019/5/21.
 */
public class UserServiceTest {

    /**
     * 自定义的缓存方案缺点：
     * 1. 缓存代码和业务代码耦合度太高，如上面的例子，AccountService 中的 getAccountByName（）方法中有了太多缓存的逻辑，不便于维护和变更
     * 2. 不灵活，这种缓存方案不支持按照某种条件的缓存，比如只有某种类型的账号才需要缓存，这种需求会导致代码的变更
     * 3. 缓存的存储这块写的比较死，不能灵活的切换为使用第三方的缓存模块
     */

    @Test
    public void test() {
        UserService s = new UserService();
        // 开始查询账号
        s.getUserByName("qiwen");// 第一次查询，应该是数据库查询
        s.getUserByName("qiwen");// 第二次查询，应该直接从缓存返回

        s.reload();// 重置缓存
        System.out.println("after reload...");

        s.getUserByName("qiwen");// 应该是数据库查询
        s.getUserByName("qiwen");// 第二次查询，应该直接从缓存返回
        s.getUserByName("qiwen");
    }
}
