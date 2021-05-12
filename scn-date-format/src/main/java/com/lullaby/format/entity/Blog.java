package com.lullaby.format.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Blog {

    // JsonFormat(object转json) 将blog对象转换成json字符串的时候，createTime将以JsonFormat中的pattern进行格式化
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    // DateTimeFormat(json转object) 指定要传入的参数的格式，如果不对应会抛出异常，pattern必须和JsonFormat中的一样
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    public Date createTime;

    @Override
    public String toString() {
        return "Blog{" +
                "createTime=" + createTime +
                '}';
    }
}
