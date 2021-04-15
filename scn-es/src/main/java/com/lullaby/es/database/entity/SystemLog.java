package com.lullaby.es.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

// _type默认为_doc?

@Data
// 配置_index，必须全部小写
@Document(indexName = "system_log")
public class SystemLog {

    // 配置_id
    @Id
    private Integer id;

    // 这里的type是啥意思?
    @Field(type = FieldType.Keyword)
    private String name;

    // 配置字段名，不配置会依据属性名自动生成
    @Field("create_time")
    private Date createTime;

}

