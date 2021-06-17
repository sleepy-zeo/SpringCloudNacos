package com.lullaby.binding.validated;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

@Data
public class User {

    @Min(value = 18, groups = {Insert.class}, message = "未成年禁止入内")
    private int id;

    @NotEmpty
    private String name;
}
