package com.yqw.boot.data.jpa.repository;

import com.yqw.boot.data.jpa.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * CrudRepository接口
 */
public interface UserRepositoryCrudRepository extends CrudRepository<User, Integer> {

}
