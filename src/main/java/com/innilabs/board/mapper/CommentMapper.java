package com.innilabs.board.mapper;

import java.util.List;

import com.innilabs.board.dto.req.CmtCreateReq;
import com.innilabs.board.entity.Comment;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    List<Comment> selectCommentsByPostId(@Param("postId") int postId, 
                                        @Param("startIndex") int startIndex, 
                                        @Param("size") int size);

	//int countCommentsByPostId(int postId);

	int insertComment(CmtCreateReq createReq);
}