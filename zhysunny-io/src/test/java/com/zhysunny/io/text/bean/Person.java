package com.zhysunny.io.text.bean;

import com.zhysunny.io.text.TextBean;
import com.zhysunny.io.text.TextIndex;

public class Person implements TextBean {
    @TextIndex(0)
    private String name;
    @TextIndex(1)
    private int age;
    @TextIndex(2)
    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
