package com.innilabs.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innilabs.board.dto.req.LoginReq;
import com.innilabs.board.dto.res.LoginRes;
import com.innilabs.board.entity.User;
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
    public String login(LoginReq loginReq , Model model, HttpServletRequest req) throws Exception {
       
        LoginRes loginRes = userService.login(loginReq,req);
        
        model.addAttribute("resultMsg", loginRes.getResultMsg());
        model.addAttribute("prevPage", loginRes.getPrevPage());
        //model.addAttribute("loginRes", loginRes);
        return loginRes.getResultPage();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession(true);
        session.invalidate();
        return "redirect:/posts/list";
    }
}