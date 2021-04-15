package com.lullaby.es.database.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

// _type默认为_doc?

@Data
@Builder
// 配置_index，必须全部小写
@Document(indexName = "frame_system_log_bean")
public class FrameSystemLog {

    // 配置_id
    @Id
    private String id;

    @Field("sort_number")
    private Integer sortNo;

    @Field(type = FieldType.Keyword)
    private String result;

    // 配置字段名，不配置会依据属性名自动生成
    @Field("create_time")
    private Date createTime;

}

