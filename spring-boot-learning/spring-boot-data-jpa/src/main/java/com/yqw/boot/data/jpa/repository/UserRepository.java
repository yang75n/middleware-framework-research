package com.yqw.boot.data.jpa.repository;

import com.yqw.boot.data.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 通过JapRepository已经定义了的函数操作数据
 * <p/>
 * 参数一 T :当前需要映射的实体
 * 参数二 ID :当前映射的实体中的OID的类型
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
