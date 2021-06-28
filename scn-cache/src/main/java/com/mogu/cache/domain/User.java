package com.mogu.cache.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1090414879337379481L;

    private int id;
    private String name;
    private int age;
}
