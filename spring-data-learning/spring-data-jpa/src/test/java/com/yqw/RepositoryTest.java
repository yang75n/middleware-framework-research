package com.yqw;


import com.yqw.data.jpa.domain.Users;
import com.yqw.data.jpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * JpaRepository接口测试
 *
 * @author Administrator
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * 查询全部数据
     */
    @Test
    public void test1() {
        List<Users> list = userRepository.findAll();
        for (Users users : list) {
            System.out.println(users);
        }
    }
}
