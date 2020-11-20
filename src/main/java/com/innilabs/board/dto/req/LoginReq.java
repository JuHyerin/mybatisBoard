package com.innilabs.board.dto.req;

import com.innilabs.board.entity.User;

import lombok.Data;


@Data
public class LoginReq {
    private String prevPage;
    //private User user;
    private String userId;
    private String password;
}