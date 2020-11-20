package com.innilabs.board.dto.res;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRes {
    private String resultMsg;
    private String resultPage;
    private String prevPage;
}