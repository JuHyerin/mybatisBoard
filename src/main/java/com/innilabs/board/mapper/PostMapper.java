package com.innilabs.board.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.innilabs.board.dto.req.ListReq;
import com.innilabs.board.entity.Comment;
import com.innilabs.board.entity.Post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
	List<Post> selectPosts(ListReq listReq);
	
	int countPosts(@Param("option") String option, @Param("word") String word);

	Post selectPostByPostId(int postId) throws SQLException;
	int countCommentsByPostId(int postId);

	Post selectPostWithComments(int postId);

	int updatePostByPost(Post post) throws SQLException;

	int deletePostByPostId(int postId) throws SQLException;

	int insertPostByPost(Post post);

	String selectWriterByPostId(int postId);

	List<Comment> getCommentsByPostId(@Param("postId") int postId, 
                                        @Param("startIndex") int startIndex, 
                                        @Param("size") int size);

}