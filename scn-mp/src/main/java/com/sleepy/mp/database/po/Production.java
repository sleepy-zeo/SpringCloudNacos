package com.sleepy.mp.database.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "production_id")
    private Long productionId;

    @TableField(value = "production_name", fill = FieldFill.DEFAULT)
    private String productionName;

    private Integer price;
    private Integer amount;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
