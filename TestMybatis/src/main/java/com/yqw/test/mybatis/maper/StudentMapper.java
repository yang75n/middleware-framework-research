package com.yqw.test.mybatis.maper;

import com.yqw.test.mybatis.model.Student;

/**
 * create table student(
 * id VARCHAR(50),
 * name VARCHAR(50) ,
 * age VARCHAR(50),
 * sex VARCHAR(50)
 * ) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
 * Created by iQiwen on 2019/4/29.
 */
public interface StudentMapper {
    /**
     * 映射器Mapper接口中的方法名跟StudentMapper.xml中的id的值相同
     *
     * @param student
     */
    void add3(Student student);
}
