package com.innilabs.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.innilabs.board.dto.Post;
import com.innilabs.board.dto.User;
import com.innilabs.board.mapper.PostMapper;
import com.innilabs.board.service.PostService;
import com.innilabs.board.util.PagingUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/posts")

public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/test")
    public String test() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @GetMapping("/list")
    public String getAllPosts(@RequestParam(name = "page", required = false) String pageParam,
            @RequestParam(name = "resultMsg", required = false) String resultMsg, Model model) {

        // page parameter 할당
        if (pageParam == null || pageParam.length() == 0 || pageParam.equals("0")) {
            pageParam = "1"; // 초기화
        }
        int page = Integer.parseInt(pageParam);

        // paging객체생성
        PagingUtil paging = new PagingUtil(page, 3, 3); // 현재페이지의 페이징객체(Paging Bar)
        paging.setTotalData(postService.countAllPosts());
        List<Post> posts = postService.selectPagedPosts(paging.getFirstData(), paging.getPageSize()); // 페이징된 데이터

        model.addAttribute("posts", posts);
        model.addAttribute("paging", paging);
        model.addAttribute("resultMsg", resultMsg);
        return "postList";
    }

    @GetMapping("/detail")
    public String detailPost(@RequestParam(name = "postId", required = true) int postId, Model model) {
        Post post = null;
        try {
            post = postService.selectPostByPostId(postId);
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
        model.addAttribute("post", post);
        return "postDetail";
    }

    @GetMapping("/create")
    public String goCreateForm(Model model, HttpServletRequest req) {
        if (postService.loginCheck(req) == null) {
            model.addAttribute("resultMsg", "로그인 후 이용");
            return "loginForm";
        }
        return "createForm";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, HttpServletRequest req) {
        String resultPage = "redirect:/posts/list";
        User user = postService.loginCheck(req);
        try {
            post.setWriter(user.getUserId());
            int resultCnt = postService.createPostByPost(post);
            if (resultCnt < 1) {
                resultPage = "createForm";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultPage;
    }

    @GetMapping("/update")
    public String goUpdateForm(@RequestParam int postId, Model model, HttpServletRequest req) {
        User user = postService.loginCheck(req);

        Post post=null;
        try {
            post = postService.selectPostByPostId(postId); //기존 내용 input에 미리 넣어줘야함
            if(user==null ){
                model.addAttribute("resultMsg", "로그인후 이용");
                return "loginForm";
            }else if(post.getWriter().equals(user.getUserId()) == false){
                //model.addAttribute("resultMsg", "본인만 수정 가능");
                String encodedParam = URLEncoder.encode("본인만 수정 가능", "UTF-8");
                return "redirect:/posts/list?resultMsg="+encodedParam;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("post", post);
        return "updateForm";
    }
    
    @PostMapping("/update")
    public String updatePost(@ModelAttribute Post post, Model model){
        String resultPage= "redirect:/posts/list";
        try {
            int resultCnt = postService.updatePostByPost(post);
            if(resultCnt<1){
                resultPage = "updateForm";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultPage;
    }
    @GetMapping("/delete")
    public String deletePost(@RequestParam int postId, Model model, HttpServletRequest req) {
        User user = postService.loginCheck(req);
        
        String resultPage= "redirect:/posts/list";
        
        Post post=null;
        try {
            post = postService.selectPostByPostId(postId);//본인여부를 확인하기 위한 해당 게시물의 작성자 조회해옴
            if(user==null ){
                model.addAttribute("resultMsg", "로그인후 이용");
                return "loginForm";
            }else if(!post.getWriter().equals(user.getUserId())){
                //model.addAttribute("resultMsg", "본인만 수정 가능");
                String encodedParam = URLEncoder.encode("본인만 수정 가능", "UTF-8");
                return resultPage+"?resultMsg="+encodedParam;
            }
            int resultCnt = postService.deletePostByPostId(postId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultPage;
    }
}