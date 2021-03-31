package com.sleepy.mp.database.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_production")
public class Production {

    // 添加了type = IdType.AUTO后，手动设置的productionId将失效
    @TableId(value = "production_id", type = IdType.AUTO)
    private Long productionId;

    private String productionName;

    private Integer price;
    private Integer amount;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
