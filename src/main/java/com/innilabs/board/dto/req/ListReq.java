package com.innilabs.board.dto.req;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Builder
@Setter
public class ListReq {
    private Integer pageParam;//null 체크
    private int startIndex;
    private int pageSize;
    //private String resultMsg;
    private String option;
    private String word;

}