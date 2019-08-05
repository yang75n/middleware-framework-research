package com.yqw.test.mybatis;

import com.yqw.test.mybatis.dao.StudentDao;
import com.yqw.test.mybatis.model.Student;
import com.yqw.test.mybatis.util.MyBatisUtil;
import org.junit.Test;

import java.sql.Connection;

/**
 * Created by iQiwen on 2019/4/29.
 */
public class Main {

    @Test
    public void testConnect() {
        Connection sqlSession = MyBatisUtil.getSqlSession().getConnection();
        System.out.println(sqlSession == null ? "连接失败" : "链接成功");
    }

    @Test
    public void testAdd1() {
        StudentDao sd = new StudentDao();
        sd.add1();
    }

    @Test
    public void testAdd2() {
        StudentDao sd = new StudentDao();
        sd.add2(new Student("64", "add2", "23", "男"));
    }

    @Test
    public void testAdd3() {
        StudentDao sd = new StudentDao();
        sd.add3(new Student("66", "add3", "23", "男"));
    }

}
