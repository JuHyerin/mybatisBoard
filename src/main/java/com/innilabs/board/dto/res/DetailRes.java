package com.innilabs.board.dto.res;

import com.innilabs.board.entity.Post;
import com.innilabs.board.util.PagingUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DetailRes {
    private Post post;
    private PagingUtil paging;
}