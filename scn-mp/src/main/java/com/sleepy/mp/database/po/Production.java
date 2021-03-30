package com.sleepy.mp.database.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_production")
public class Production {

    private long productionId;

    private String productionName;

    private int price;
    private int amount;
    @TableField(exist = false)
    private Date createTime;
}
