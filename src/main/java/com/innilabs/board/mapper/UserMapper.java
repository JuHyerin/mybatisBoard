package com.innilabs.board.mapper;

import com.innilabs.board.dto.User;

import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //@Select("SELECT * FROM USER WHERE id=#{id}")
    public User selectUserById(String id);
}