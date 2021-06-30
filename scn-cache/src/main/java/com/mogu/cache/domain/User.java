package com.mogu.cache.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1090414879337379481L;

    @TableId
    private Integer id;
    private String name;
    private Integer age;
}
