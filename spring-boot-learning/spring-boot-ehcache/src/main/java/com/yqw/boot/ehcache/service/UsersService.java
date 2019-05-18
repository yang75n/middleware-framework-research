package com.yqw.boot.ehcache.service;

import com.yqw.boot.ehcache.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {

    List<User> findUserAll();

    User findUserById(Integer id);

    Page<User> findUserByPage(Pageable pageable);

    void saveUsers(User users);
}
