package com.yqw.my.cache;

/**
 * 我自己做缓存
 * Created by iQiwen on 2019/5/21.
 */
public class UserService {

    private MyCacheManager<User> myCacheManager;

    public UserService() {
        myCacheManager = new MyCacheManager<User>();//构造一个缓存管理器
    }


    public User getUserByName(String acctName) {
        //1. 首先查询缓存
        User result = myCacheManager.getValue(acctName);
        if (result != null) {
            // 如果在缓存中，则直接返回缓存的结果
            System.out.println("get from cache..." + acctName);
            return result;
        }
        //2. 否则到数据库中查询
        result = getFromDB(acctName);
        //3. 将数据库查询的结果更新到缓存中
        if (result != null) {
            myCacheManager.addOrUpdateCache(acctName, result);
        }
        return result;
    }

    public void reload() {
        myCacheManager.evictCache();
    }

    private User getFromDB(String name) {
        System.out.println("get from  db ..." + name);
        return new User(name, 0);
    }
}
