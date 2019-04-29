package com.yqw.test.mybatis.dao;

import com.yqw.test.mybatis.maper.StudentMapper;
import com.yqw.test.mybatis.model.Student;
import com.yqw.test.mybatis.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class StudentDao {

    /*
     * 增加的方法1
     */
    public void add1() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSession();
            //事务开始（默认）

            //读取StudentMapper.xml配置文件中的sql语句，对应的sql语句由{名称空间.sql的id指定}
            int i = sqlSession.insert("studentNamespace.add1");
            System.out.println("本次操作影响了" + i + "行数据");

            //事务提交
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            sqlSession.rollback();
            //throw e;
        } finally {
            MyBatisUtil.closeSqlSession();
        }
    }

    /*
     * 增加的方法2
     */
    public void add2(Student stu) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSession();
            //事务开始（默认）

            //读取StudentMapper.xml配置文件中的sql语句
            int i = sqlSession.insert("studentNamespace.add2", stu);
            System.out.println("本次操作影响了" + i + "行数据");

            //事务提交
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            sqlSession.rollback();
            //throw e;
        } finally {
            MyBatisUtil.closeSqlSession();
        }
    }

    /*
     * 增加的方法3
     * 往数据库添加记录必须要添加事务，没有事务添加不进去
     */
    public void add3(Student stu) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSession();
            //事务开始（默认）

            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            studentMapper.add3(stu);
            System.out.println("add3 本次操作结束");
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //事务回滚
            sqlSession.rollback();
            // throw e;
        } finally {
            MyBatisUtil.closeSqlSession();
        }
    }


}