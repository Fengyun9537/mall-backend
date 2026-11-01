package com.lu.mallbackend.mapper;

import com.lu.mallbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 手写一句最简单的 SQL 查用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
}