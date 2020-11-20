package com.innilabs.board.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmtCreateReq {
    private String commentWriter;
    private String comment;
    private int postId;
}