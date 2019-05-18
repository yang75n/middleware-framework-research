package com.yqw.boot.data.jpa.repository;

import com.yqw.boot.data.jpa.domain.User;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * 按照Repository接口的方法名称命名查询
 */
public interface UserRepositoryByName extends Repository<User, Integer> {

    /**
     * 方法的名称必须要遵循驼峰式命名规则。findBy(关键字)+属性名称(首字母要大写)+查询条件(首字母大写)
     */
    List<User> findByName(String name);

    List<User> findByNameAndAge(String name, Integer age);

    List<User> findByNameLike(String name);
}
