package com.sleepy.mp.database.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleepy.mp.database.po.Production;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ProductionMapper extends BaseMapper<Production> {

}
