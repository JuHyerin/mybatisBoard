package com.innilabs.board.repository;

import java.util.List;

import com.innilabs.board.dto.Post;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;
@Repository
@MapperScan(basePackages = "com.innilabs.boards.mapper")
public interface PostDao {
    
    List<Post> selectPosts();
}