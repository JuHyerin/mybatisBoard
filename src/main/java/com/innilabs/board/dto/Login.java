package com.innilabs.board.dto;

import lombok.Data;

@Data
public class Login {
    private String loginButtonMsg;
    private String loginURL;
    private boolean loginCheck;
    /* public boolean getLoginCheck(){
        return this.loginCheck;
    } */
}