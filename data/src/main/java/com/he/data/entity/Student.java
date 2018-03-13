package com.he.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hesh on 2018/2/9.
 */

@Entity
public class Student {

    @Id(autoincrement = true)
    private Long id;

    @Index(unique = true)
    private long Code;//学籍号

    private String name;
    private String sex;
    private int age;
    private int score;

    @Generated(hash = 1544762622)
    public Student(Long id, long Code, String name, String sex, int age,
            int score) {
        this.id = id;
        this.Code = Code;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.score = score;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getCode() {
        return this.Code;
    }

    public void setCode(long Code) {
        this.Code = Code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Code=" + Code +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

}
