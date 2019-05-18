package com.yqw.boot.ehcache.service.impl;


import com.yqw.boot.ehcache.domain.User;
import com.yqw.boot.ehcache.repository.UsersRepository;
import com.yqw.boot.ehcache.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UsersService接口实现类
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<User> findUserAll() {
        return this.usersRepository.findAll();
    }


    /**
     * @Cacheable: 对当前查询的对象做缓存处理
     * users指的是配置文件中配置的缓存策略ID
     * 被缓存的对象要实现序列化
     */
    @Cacheable(value = "users")//value指定缓存策略，key给缓存起个别名查询哟相同名称时则直接返回。
    @Override
    public User findUserById(Integer id) {
        System.out.println("findUserById");
        // return usersRepository.findOne(id);
        return usersRepository.getOne(id);
    }

    @Override
    @Cacheable(value = "users", key = "#pageable.pageSize")
    public Page<User> findUserByPage(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    /**
     * @CacheEvict 作用：清除缓存
     */
    @Override
    //@CacheEvict(value="users",allEntries=true) 清除缓存中以users缓存策略缓存的对象
    @CacheEvict(value = "users", allEntries = true)
    public void saveUsers(User users) {
        usersRepository.save(users);
    }

}
