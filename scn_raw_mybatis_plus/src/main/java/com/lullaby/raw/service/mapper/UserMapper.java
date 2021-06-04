package com.lullaby.raw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lullaby.raw.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where uid in <foreach collection=\"uids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">${id}</foreach>")
    Set<User> selectUsers(@Param("uids") List<Integer> uids);
}
