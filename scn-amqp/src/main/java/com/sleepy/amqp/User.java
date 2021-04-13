package com.sleepy.amqp;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -4246053565943298274L;

    private String name;
    private int age;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.age = 24;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
