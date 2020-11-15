package com.innilabs.board.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innilabs.board.dto.Post;
import com.innilabs.board.dto.User;
import com.innilabs.board.mapper.PostMapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MapperScan(basePackages = "com.innilabs.board.mapper")
public class PostService {
    @Autowired
    private PostMapper postMapper;

    public int countAllPosts() {
        return postMapper.countAllPosts();
    }

    public List<Post> selectPagedPosts(int startIndex, int size) {
        return postMapper.selectPagedPosts(startIndex, size);
    }

    public Post selectPostByPostId(int postId) throws SQLException {
		return postMapper.selectPostByPostId(postId);
	}

	public int updatePostByPost(Post post) throws SQLException {
		return postMapper.updatePostByPost(post);
	}

	public int deletePostByPostId(int postId) throws SQLException{
		return postMapper.deletePostByPostId(postId);
	}

	public int createPostByPost(Post post) throws SQLException{
		return postMapper.insertPostByPost(post);
	}

	public User loginCheck(HttpServletRequest req){//Session에 넣어둔 로그인된 사용자 정보 가져옴
		HttpSession session = req.getSession(true);
		User user = (User) session.getAttribute("userInfo");
		return user;
	}
}