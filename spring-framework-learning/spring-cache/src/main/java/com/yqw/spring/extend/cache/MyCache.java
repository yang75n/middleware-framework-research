package com.yqw.spring.extend.cache;

import com.yqw.spring.cache.User;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 我自己写的cache，实现Cache接口，可以交给Spring管理从而使用@Cacheable注解。
 * 在此实现里面使用的是Map，当然也可以用Redis或者chcache或者memerycached等第三方缓存组件
 */
public class MyCache implements Cache {
    private String name;
    private Map<String, User> store = new HashMap<String, User>();


    public MyCache() {
    }

    public MyCache(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object getNativeCache() {
        return store;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper result = null;
        User thevalue = store.get(key);
        if (thevalue != null) {
            thevalue.setId(0);
            result = new SimpleValueWrapper(thevalue);
        }
        return result;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T) store.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        User thevalue = (User) value;
        store.put((String) key, thevalue);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
    }

    @Override
    public void clear() {
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }
}
