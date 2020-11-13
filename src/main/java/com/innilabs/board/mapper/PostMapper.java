package com.innilabs.board.mapper;

import java.util.List;

import com.innilabs.board.dto.Post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostMapper {
    @Select("SELECT * FROM POST")
    List<Post> selectPosts();

	List<Post> selectPagedPosts(@Param("startIndex") int startIndex, @Param("size") int size);

    @Select("SELECT COuNT(*) FROM post WHERE is_deleted=0")
	int CountAllPosts();


}