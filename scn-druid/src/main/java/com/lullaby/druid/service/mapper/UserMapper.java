package com.lullaby.druid.service.mapper;

import com.lullaby.druid.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> select();

    @Select("select * from user where uid = #{uid}")
    User selectOne(@Param("uid") int uid);
}
