package com.innilabs.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.innilabs.board.dto.req.DetailReq;
import com.innilabs.board.dto.req.ListReq;
import com.innilabs.board.dto.res.ListRes;
import com.innilabs.board.entity.Post;
import com.innilabs.board.entity.User;
import com.innilabs.board.exception.BoardException;
import com.innilabs.board.service.PostService;
import com.innilabs.board.util.LoginUtil;
import com.innilabs.board.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    /*
     * @Autowired private CommentService commentService;
     */

    static final String LIST_PAGE = "redirect:/posts/list"; // final 상수 표현

    @GetMapping("/list")
    public String getAllPosts(HttpServletRequest req, Model model, ListReq listReq) throws Exception {

        // 로그인 여부 -> 로그인 || userId사용중 로그아웃 출력
        // boolean loginCheck = false;
        String welcome = "";
        User selectedUser = LoginUtil.loginCheck(req);
        if (selectedUser != null) {
            // loginCheck = true;
            welcome = selectedUser.getUserId() + "사용중";
        }

        ListRes lr = postService.listPosts(listReq, welcome);

        model.addAttribute("listRes", lr);
        return "postList";
    }

    @GetMapping("/detail")
    public String detailPost(Model model, DetailReq detailReq) throws SQLException {

        model.addAttribute("detailRes", postService.detailPost(detailReq));

        return "postDetail";
    }

    @GetMapping("/create")
    public String goCreateForm(Model model, HttpServletRequest req) {
        if (LoginUtil.loginCheck(req) == null) {
            model.addAttribute("resultMsg", "로그인 후 이용");
            model.addAttribute("prevPage", "redirect:/posts/create");
            return "loginForm";
        }
        return "createForm";
    }

    @PostMapping("/create")
    public String createPost(Post post, HttpServletRequest req) throws Exception {
        // 로그인 체크해야함 url 다 보이니께
        User user = LoginUtil.loginCheck(req);
        boolean loginCheck = StringUtil.equal(user.getUserId(), post.getWriter());
        if(!loginCheck){
            throw new BoardException("로그인 문제", "/posts/list");
        }
        if (postService.createPost(post, user) < 1) {
            throw new BoardException("게시물 생성 실패", "/posts/create");
        }
        return LIST_PAGE;
    }

    @GetMapping("/update")
    public String goUpdateForm(int postId, Model model, HttpServletRequest req) throws SQLException {
        User user = LoginUtil.loginCheck(req);
        if (user == null) {
            model.addAttribute("resultMsg", "로그인후 이용");
            return "loginForm";
        }
        // 순서 조정 -> 퍼포먼스
        Post existPost = postService.selectPostByPostId(postId); // 로그인확인 후에 게시물 가져오기!
        if (!existPost.getWriter().equals(user.getUserId())) {
            // 기존 내용 input에 미리 넣어줘야함

            // String encodedParam = URLEncoder.encode("본인만 수정 가능", "UTF-8");
            // return "redirect:/posts/list?resultMsg="+encodedParam;
            // model.addAttribute("resultMsg", "본인만 수정 가능");
            return LIST_PAGE + "?resultMsg=" + "onlywriter";
        }

        model.addAttribute("existPost", existPost);
        return "updateForm";
    }

    @PostMapping("/update")
    public String updatePost(Post updatedPost, Model model) {

        int resultCnt = postService.updatePost(updatedPost);
        if (resultCnt < 1) {
            return "updateForm";
        }
        return LIST_PAGE;
    }

    @GetMapping("/delete")
    public String deletePost(int postId, Model model, HttpServletRequest req) throws BoardException {
        User user = LoginUtil.loginCheck(req);

        /* if(user==null ){//회원 여부
            model.addAttribute("resultMsg", "로그인후 이용");
            return "loginForm";
        } */
        
        if(!StringUtil.equal(postService.getWriter(postId), user.getUserId())){//본인여부
            model.addAttribute("resultMsg", "본인만 삭제 가능");
            return "loginForm";
        }

        postService.deletePost(postId);
        return LIST_PAGE;
    }
    
}