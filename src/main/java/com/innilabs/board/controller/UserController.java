package com.innilabs.board.controller;

import com.innilabs.board.dto.User;
import com.innilabs.board.mapper.UserMapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@MapperScan(basePackages = "com.innilabs.board.mapper")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserMapper UserMapper;
    @GetMapping("/list/{id}")
    public User getUserById(@PathVariable String id){
        return UserMapper.selectUserById(id);
    }
}