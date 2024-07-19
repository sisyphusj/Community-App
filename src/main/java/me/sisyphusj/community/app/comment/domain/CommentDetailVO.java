package me.sisyphusj.community.app.comment.domain;

import java.util.Date;

import lombok.Getter;

@Getter
public class CommentDetailVO {

	private long commentId;

	private Long parentId;

	private long postId;

	private String author;

	private String content;

	private HasChild hasChild;

	private Date createdDate;

	private Date updatedDate;

}
