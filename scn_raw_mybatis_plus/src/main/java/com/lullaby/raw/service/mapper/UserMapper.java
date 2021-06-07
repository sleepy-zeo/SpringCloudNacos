package com.lullaby.raw.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lullaby.raw.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 传入List or Set
     *
     * collection:  指定要遍历的集合
     * item:        将当前遍历出的元素赋值给指定的变量
     * separator:   每个元素之间的分隔符
     * open:        遍历出所有结果的前开始的字符
     * close:       遍历出所有结果的后结束的字符
     */
    @Select("<script>" +
            "select * from user where uid in " +
            "<foreach collection=\"uids\" item=\"uid\" open=\"(\" separator=\",\" close=\")\">" +
            "${uid}" +
            "</foreach>" +
            "</script>")
    Set<User> selectUsersWithList(@Param("uids") Collection<Integer> uids);

    /**
     * 传入Map(简单方式)
     */
    @Select("select * from user where uid = #{pairs.uid} and name=#{pairs.name}")
    User selectUser(@Param("pairs") Map<String, Object> pairs);

    /**
     * 传入Map(复杂方式)
     * 1. <where></where>标签会自动去掉不符合sql的and
     * 2. like使用CONCAT进行字符串拼接
     * 3. 通过if或者等价的when进行条件选择
     */
    @Select("<script> select * from user " +
            "<where>" +
            "<if test=\"pairs.isDel != null\">" +
            "    AND is_del = #{pairs.isDel}" +
            "</if>" +
            "<if test=\"pairs.uid != null\">" +
            "    AND uid like CONCAT('%',#{pairs.uid},'%')" +
            "</if>" +
            "<if test=\"pairs.name != null\">" +
            "    AND name like CONCAT('%',#{pairs.name},'%')" +
            "</if>" +
            "</where>" +
            "</script>")
    Set<User> selectUsersWithMap(@Param("pairs") Map<String, Object> pairs);
}
