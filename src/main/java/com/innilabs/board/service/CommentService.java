package com.innilabs.board.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innilabs.board.dto.req.CmtCreateReq;
import com.innilabs.board.entity.Comment;
import com.innilabs.board.entity.User;
import com.innilabs.board.mapper.CommentMapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MapperScan(basePackages = "com.innilabs.board.mapper")
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

	/* public int countCommentsByPostId(int postId) {
		return commentMapper.countCommentsByPostId(postId);
	} */

	public List<Comment> selectCommentsByPostId(int postId, int firstData, int pageSize) {
		return commentMapper.selectCommentsByPostId(postId, firstData, pageSize);
	}

	public int createComment(CmtCreateReq createReq) {
        return commentMapper.insertComment(createReq);
    }
    
    public User loginCheck(HttpServletRequest req){//Session에 넣어둔 로그인된 사용자 정보 가져옴
		HttpSession session = req.getSession(true);
		User user = (User) session.getAttribute("userInfo");
		return user;
	}
}