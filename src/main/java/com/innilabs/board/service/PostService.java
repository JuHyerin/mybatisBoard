package com.innilabs.board.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.soap.Detail;

import com.innilabs.board.dto.req.DetailReq;
import com.innilabs.board.dto.req.ListReq;
import com.innilabs.board.dto.res.DetailRes;
import com.innilabs.board.dto.res.ListRes;
import com.innilabs.board.entity.Comment;
import com.innilabs.board.entity.Post;
import com.innilabs.board.entity.User;
import com.innilabs.board.mapper.PostMapper;
import com.innilabs.board.util.LoginUtil;
import com.innilabs.board.util.PagingUtil;
import com.innilabs.board.util.StringUtil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@MapperScan(basePackages = "com.innilabs.board.mapper")
@Slf4j
public class PostService {
    @Autowired
    private PostMapper postMapper;

	public ListRes listPosts(ListReq listReq){

		// page parameter 초기화
        if (listReq.getPageParam() == null || listReq.getPageParam() == 0) {
			listReq.setPageParam(1);
		}

		// search parameter 초기화
		if(listReq.getWord()== null){
			listReq.setWord("");
		}
		String option = listReq.getOption();
		String word = listReq.getWord();

        // paging객체생성
        PagingUtil paging = new PagingUtil(listReq.getPageParam(), 3, 3); // 현재페이지의 페이징객체(Paging Bar)
        paging.setTotalData(postMapper.countPosts(option, word));
		listReq.setStartIndex(paging.getFirstData());
		listReq.setPageSize(paging.getPageSize());

        List<Post> posts = postMapper.selectPosts(listReq); //검색된 데이터 페이징

		//응답 객체 생성하여 반환
		return ListRes
					.builder()
					.posts(posts)
					.paging(paging)
					.build();
	}
	

    public Post selectPostByPostId(int postId){
		try {
			return postMapper.selectPostByPostId(postId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("createPost", e);
			return null;
		}
	}

	public DetailRes detailPost(DetailReq detailReq)  {
		Post post = null;
		//page parameter 초기화
        if (detailReq.getPageParam() == null || detailReq.getPageParam() == 0) {
        	detailReq.setPageParam(1);
        }

        // 댓글 paging
        PagingUtil paging = new PagingUtil(detailReq.getPageParam(), 3, 2);
		paging.setTotalData(postMapper.countCommentsByPostId(detailReq.getPostId()));
		List<Comment> comments = postMapper.getCommentsByPostId(detailReq.getPostId(), paging.getFirstData(), paging.getPageSize());

		try {
			post = postMapper.selectPostByPostId(detailReq.getPostId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		post.setComments(comments);
		//post = postMapper.selectPostWithComments(detailReq.getPostId());
		System.out.println(post);
        
		return DetailRes
					.builder()
					.post(post)
					.paging(paging)
					.build();
	}
	public int updatePost(Post post){
		try {
			return postMapper.updatePostByPost(post);
		} catch (SQLException e) {
			log.error("updatePost", e);
			return 0;
		}
	}

	public int deletePost(int postId){
		try {
			return postMapper.deletePostByPostId(postId);
		} catch (SQLException e) {
			log.error("deletePost", e);
			return 0;
		}
	}

	public int createPost(Post post, HttpServletRequest req) {
		User user = LoginUtil.loginCheck(req);
		post.setWriter(user.getUserId());
		
		return postMapper.insertPostByPost(post);
	}




	public String getWriter(int postId) {
		return postMapper.selectWriterByPostId(postId);
	}

}