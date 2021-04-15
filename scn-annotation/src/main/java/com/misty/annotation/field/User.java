package com.misty.annotation.field;

import lombok.Data;

@Data
public class User {

    private int id;
    @Check(paramValues = {"male", "female"})
    private String sex;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", sex='" + sex + '\'' +
                '}';
    }
}


