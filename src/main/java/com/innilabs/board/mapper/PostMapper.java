package com.innilabs.board.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.innilabs.board.dto.Post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostMapper {
	//List<Post> selectPagedPosts(@Param("startIndex") int startIndex, @Param("size") int size);
	List<Post> selectPosts(@Param("stringParam") Map<String, String> stringParam,
							@Param("integerParam") Map<String, Integer> integerParam);
	
	int countPosts(@Param("option") String option, @Param("word") String word);

	Post selectPostByPostId(int postId) throws SQLException;

	int updatePostByPost(Post post) throws SQLException;

	int deletePostByPostId(int postId) throws SQLException;

	int insertPostByPost(Post post);

}