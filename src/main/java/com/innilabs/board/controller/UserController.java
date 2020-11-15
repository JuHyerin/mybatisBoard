package com.innilabs.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innilabs.board.dto.User;
import com.innilabs.board.mapper.UserMapper;
import com.innilabs.board.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller

@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String goLoginForm(){
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletRequest req){
        String resultPage="";
        String resultMsg="";
        User selectedUser = userService.getUserByUserId(user);
        if(selectedUser!=null){
            if(user.getPassword().equals(selectedUser.getPassword())){
                resultPage = "redirect:/posts/list";
                HttpSession session = req.getSession(true);
                session.setAttribute("userInfo", selectedUser);
            }
            else{
                resultPage = "loginForm";
                resultMsg = "비밀번호 일치 안함";
            }
        }
        else{
            resultPage = "loginForm";
                resultMsg = "아이디 존재 안함";
        }
        model.addAttribute("resultMsg", resultMsg);
        return resultPage;
    }
    
    
}