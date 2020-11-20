package com.innilabs.board.dto.res;

import java.util.List;

import com.innilabs.board.entity.Post;
import com.innilabs.board.util.PagingUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ListRes {
    private List<Post> posts;
    private PagingUtil paging;
    private String resultMsg;
    private boolean loginCheck;
    private String welcome;
    //검색 view에서 default로 출력하는 용도
    private String option;
    private String word;

}