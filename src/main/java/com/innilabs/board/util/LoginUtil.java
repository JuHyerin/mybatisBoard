package com.innilabs.board.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.innilabs.board.entity.User;

public class LoginUtil {
    static public User loginCheck(HttpServletRequest req){//Session에 넣어둔 로그인된 사용자 정보 가져옴
		HttpSession session = req.getSession(true);
		User user = (User) session.getAttribute("userInfo");
		return user;
	}
}