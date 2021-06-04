package com.lullaby.raw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lullaby.raw.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
