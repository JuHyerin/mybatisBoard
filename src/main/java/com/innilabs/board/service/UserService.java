package com.innilabs.board.service;

import com.innilabs.board.dto.User;
import com.innilabs.board.mapper.UserMapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MapperScan(basePackages = "com.innilabs.board.mapper")
public class UserService {
    @Autowired
    UserMapper userMapper;

	public User getUserByUserId(User user) {
		return userMapper.selectUserByUserId(user);
	}
    
}