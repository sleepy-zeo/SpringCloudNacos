package com.mogu.cache.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mogu.cache.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
