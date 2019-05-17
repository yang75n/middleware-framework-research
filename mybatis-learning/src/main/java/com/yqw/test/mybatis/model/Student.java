package com.yqw.test.mybatis.model;

/**
 * create table student(
 * id VARCHAR(50),
 * name VARCHAR(50) ,
 * age VARCHAR(50),
 * sex VARCHAR(50)
 * ) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
 */
public class Student {

    private String id;
    private String name;
    private String age;
    private String sex;


    public Student() {
        super();
    }

    public Student(String id, String name, String age, String sex) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


}