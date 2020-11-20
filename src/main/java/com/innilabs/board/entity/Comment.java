package com.innilabs.board.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Comment {
    private int commentId;
    private int postId;
    private String commentWriter;
    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentUpdatedAt;
    private LocalDateTime commentDeletedAt;
    private boolean commentIsDeleted;
    private String comment; 
}