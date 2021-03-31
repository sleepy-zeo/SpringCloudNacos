package com.sleepy.mp.database.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleepy.mp.database.po.Production;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface ProductionMapper extends BaseMapper<Production> {

    @Select("SELECT * from tb_production where production_id=#{id}")
    Production queryPrimitive(@Param("id") int id);

    @Select("SELECT * from tb_production ${ew.customSqlSegment}")
    IPage<Production> query(Page<?> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("SELECT * from tb_production ${ew.customSqlSegment}")
    IPage<Map<String, Object>> query2(Page<?> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
