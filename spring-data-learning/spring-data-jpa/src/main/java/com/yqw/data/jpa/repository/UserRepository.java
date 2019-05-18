package com.yqw.data.jpa.repository;


import com.yqw.data.jpa.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository接口讲解
 * 使用JpaRepository中自带的函数，更多Repository用法间Spring-boot-data-jpa
 *
 * @author Administrator
 */
public interface UserRepository extends JpaRepository<Users, Integer> {

}
