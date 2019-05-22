package com.yqw.spring.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * 使用Spring注解换成,使用spring默认缓存实现
 */
public class UserService {
    @Cacheable(value = "userCache")// 使用了一个缓存名叫 userCache
    public User getUserByName(String userName) {
        // 方法内部实现不考虑缓存逻辑，直接实现业务
        System.out.println("getUserByName ," + userName);
        return getFromDB(userName);
    }

    @CacheEvict(value = "userCache", key = "#user.getName()")// 清空 accountCache 缓存
    public void updateUser(User user) {
        updateDB(user);
    }

    @CacheEvict(value = "userCache", allEntries = true)// 清空 accountCache 缓存
    public void reload() {
    }


    @Cacheable(value = "userCache", condition = "#userName.length() <= 4")// 缓存名叫 userCache
    public User getAccountByNameWithCondition(String userName) {
        // 方法内部实现不考虑缓存逻辑，直接实现业务
        return getFromDB(userName);
    }

    @Cacheable(value = "userCache", key = "#userName.concat(#password)")
    public User getAccount(String userName, String password) {
        // 方法内部实现不考虑缓存逻辑，直接实现业务
        int id = 0;
        return getFromDB(userName, id);

    }

    @CachePut(value = "userCache", key = "#account.getName()")// 更新 accountCache 缓存
    public User updateAccountAndCache(User account) {
        return updateDBAndReturnValue(account);
    }


    private User getFromDB(String name) {
        System.out.println("get from db..." + name);
        return new User(name, 0);
    }

    private void updateDB(User user) {
        System.out.println("real update db..." + user.getName());
    }


    private User getFromDB(String acctName, int id) {
        System.out.println("real querying db..." + acctName);
        return new User(acctName, id);
    }

    private User updateDBAndReturnValue(User account) {
        System.out.println("real updating db..." + account.getName());
        return account;
    }
}