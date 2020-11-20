package com.innilabs.board.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post implements Serializable{
    
	private static final long serialVersionUID = 3351906259819685774L;

	private int postId;
	private String title;
	private String writer;
	private String contents;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime deletedAt;
	private boolean isDeleted;

	private List<Comment> comments;
}