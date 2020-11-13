package com.innilabs.board.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.innilabs.board.dto.Post;
import com.innilabs.board.mapper.PostMapper;
import com.innilabs.board.util.PagingUtil;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/posts")
@MapperScan(basePackages = "com.innilabs.board.mapper")
public class PostController {
    @Autowired
    private PostMapper postMapper;
    @GetMapping("/test")
    public String test(){
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);    
    }

    @GetMapping("/list")
    public String getAllPosts(@RequestParam(name = "page", required = false) String pageParam, Model model) {
        //page parameter 할당
		if(pageParam==null || pageParam.length()==0 || pageParam.equals("0")) {
			pageParam = "1"; //초기화
        }
        int page = Integer.parseInt(pageParam);
        
        //paging객체생성
        PagingUtil paging = new PagingUtil(page, 3, 3); //현재페이지의 페이징객체(Paging Bar)
        paging.setTotalData(postMapper.CountAllPosts()); 
        List<Post> posts = postMapper.selectPagedPosts(paging.getFirstData(), paging.getPageSize()); //페이징된 데이터
        
        model.addAttribute("posts", posts);
        model.addAttribute("paging", paging);

        return "postList";
    }

    @GetMapping("/create")
    public String createPost(){
        return "postForm";
    }

    @GetMapping("/detail")
    public String detailPost(){
        return "postDetail";
    }

}