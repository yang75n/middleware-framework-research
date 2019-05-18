package com.yqw.boot.web.repository;

import com.yqw.boot.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by iQiwen on 2019/5/16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 通过一定规则的命名方式，自动生成SQL
     */
    User findByUserName(String userName);

    User findByUserNameOrEmail(String username, String email);

}
