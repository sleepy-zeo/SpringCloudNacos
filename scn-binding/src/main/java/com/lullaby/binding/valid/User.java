package com.lullaby.binding.valid;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class User {

    @Min(value = 18, message = "未成年禁止入内")
    private Integer age;
}
