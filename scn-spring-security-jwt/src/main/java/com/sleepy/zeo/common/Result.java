package com.sleepy.zeo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private HttpStatus code;
    private String desc;
    private String token;
    private Object data;
}
