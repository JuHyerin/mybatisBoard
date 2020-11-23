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
import com.innilabs.board.exception.BoardException;
import com.innilabs.board.mapper.PostMapper;
import com.innilabs.board.util.LoginUtil;
import com.innilabs.board.util.PagingUtil;
import com.innilabs.board.util.StringUtil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@MapperScan(basePackages = "com.innilabs.board.mapper")
@Slf4j
@RequiredArgsConstructor
public class PostService {
    
    final PostMapper postMapper;

	static final String LIST_PAGE = "redirect:/posts/list";
	
	final static int PAGE_SIZE = 3;
	final static int BLOCK_SIZE = 2;	

	public ListRes listPosts(ListReq listReq, String welcome) throws Exception {

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
		if(posts.size()==0){ //view에서 다시 처리하기 , 예외 ㄴㄴ
			throw new SQLException();
		}
		//응답 객체 생성하여 반환
		return ListRes
					.builder()
					.posts(posts)
					.paging(paging)
					.option(option)
					.word(word)
					.welcome(welcome)
					.loginCheck(welcome.equals("")?false:true)
					.build();
	}
	

    public Post selectPostByPostId(int postId) throws SQLException {
		
		return postMapper.selectPostByPostId(postId);
		
	}

	public DetailRes detailPost(DetailReq detailReq) throws SQLException {
		Post post = null;
		//page parameter 초기화
        if (detailReq.getPageParam() == null || detailReq.getPageParam() == 0) {
        	detailReq.setPageParam(1);
        }

		//함수로 빼기
        // 댓글 paging
        PagingUtil paging = new PagingUtil(detailReq.getPageParam(), PAGE_SIZE, BLOCK_SIZE);//상수는 final static
		paging.setTotalData(postMapper.countCommentsByPostId(detailReq.getPostId()));
		//left join으로 하기
		List<Comment> comments = postMapper.getCommentsByPostId(detailReq.getPostId(), paging.getFirstData(), paging.getPageSize());

		post = postMapper.selectPostByPostId(detailReq.getPostId());
		if(post==null){
			throw new SQLException();
		}
		post.setComments(comments);
		//post = postMapper.selectPostWithComments(detailReq.getPostId());
		//System.out.println(post);
        
		return DetailRes
					.builder()
					.post(post)
					.paging(paging)
					.build();
	}

	//사용자에게 문제 인지
	public int updatePost(Post post){
		try {
			return postMapper.updatePostByPost(post);
		} catch (SQLException e) {
			log.error("updatePost", e);
			return 0;
		}
	}
	//사용자에게 문제 인지
	public int deletePost(int postId) throws BoardException {
		try {
			return postMapper.deletePostByPostId(postId);
		} catch (SQLException e) {
			log.error("deletePost", e);
			throw new BoardException("삭제실패", LIST_PAGE); //custom exception 하면 다 처리 ㄱㄴ
		}
	}

	public int createPost(Post post, User user) {//param으로 User 넘기기, req ㄴㄴ
		
		post.setWriter(user.getUserId());
		
		return postMapper.insertPostByPost(post);
	}




	public String getWriter(int postId) {
		return postMapper.selectWriterByPostId(postId);
	}


}