package com.innilabs.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innilabs.board.dto.req.LoginReq;
import com.innilabs.board.dto.res.LoginRes;
import com.innilabs.board.entity.User;
import com.innilabs.board.mapper.UserMapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@MapperScan(basePackages = "com.innilabs.board.mapper")
@Slf4j
public class UserService {
    @Autowired
    UserMapper userMapper;

	public LoginRes login(LoginReq loginReq, HttpServletRequest req) throws Exception {
		String resultPage="";
		String resultMsg="";
		
		User selectedUser = userMapper.selectUserByUserId(loginReq.getUserId());
		//User selectedUser = userMapper.selectUserByUserId(loginReq.getUserId()).orElseThrow(()->new NullPointerException());
		if(selectedUser!=null){
            if(loginReq.getPassword().equals(selectedUser.getPassword())){//로그인성공
				
				if(loginReq.getPrevPage()!=null){
					resultPage = loginReq.getPrevPage();
				}else{
					resultPage = "redirect:/posts/list";
				}
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
		if(loginReq.getPrevPage()!=null){
			return LoginRes
				.builder()
				.resultMsg(resultMsg)
				.resultPage(resultPage)
				.prevPage(loginReq.getPrevPage())
				.build();
		}
		else{
			return LoginRes
				.builder()
				.resultMsg(resultMsg)
				.resultPage(resultPage)
				.build();
		}
		
	}
    
}