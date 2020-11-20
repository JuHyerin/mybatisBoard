package com.innilabs.board.controller;

import javax.servlet.http.HttpServletRequest;

import com.innilabs.board.dto.req.CmtCreateReq;
import com.innilabs.board.entity.Comment;
import com.innilabs.board.entity.User;
import com.innilabs.board.service.CommentService;
import com.innilabs.board.util.LoginUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public String createComment(CmtCreateReq createReq, HttpServletRequest req, Model model){
        User user = LoginUtil.loginCheck(req);
        if(user == null){
            
            model.addAttribute("resultMsg", "로그인후 이용");

            model.addAttribute("prevPage", "redirect:/posts/detail?postId="+createReq.getPostId());
            return "loginForm";
        }
        commentService.createComment(createReq);
        return "redirect:/posts/detail?postId=" + createReq.getPostId();
    }

}