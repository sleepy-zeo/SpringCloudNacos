package com.lullaby.mb.service.mapper;

import com.lullaby.mb.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("select * from user where uid = #{uid}")
    User getUser(@Param("uid") int uid);

    @Select("select * from user")
    List<User> getUsers();

    @Insert("insert into user values(#{uid}, #{name})")
    void insertUser(User user);
}
