package com.sleepy.mp.database.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_production")
public class Production extends BasePojo {

    @TableId(value = "production_id")
    private Long productionId;
    private String productionName;
    private Integer price;
    private Integer amount;
}
