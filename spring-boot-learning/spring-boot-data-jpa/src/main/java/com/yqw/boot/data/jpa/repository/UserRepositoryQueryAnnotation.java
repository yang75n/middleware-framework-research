package com.yqw.boot.data.jpa.repository;

import com.yqw.boot.data.jpa.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository   @Query
 */
public interface UserRepositoryQueryAnnotation extends Repository<User, Integer> {

    @Query("from Users where name = ?")
    List<User> queryByNameUseHQL(String name);

    @Query(value = "select * from t_users where name = ?", nativeQuery = true)
    List<User> queryByNameUseSQL(String name);

    @Query("update Users set name  = ? where id  = ?")
    @Modifying
        //需要执行一个更新操作
    void updateUserNameById(String name, Integer id);
}
