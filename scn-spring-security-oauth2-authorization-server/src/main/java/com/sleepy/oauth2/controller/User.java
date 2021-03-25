package com.sleepy.oauth2.controller;

public class User {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User[" +
                "name='" + name + '\'' +
                ']';
    }
}
