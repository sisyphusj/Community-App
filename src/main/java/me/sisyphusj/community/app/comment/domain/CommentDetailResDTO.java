package me.sisyphusj.community.app.comment.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDetailResDTO {

	private long commentId;

	private long userId;

	private Long parentId;

	private long postId;

	private String author;

	private String content;

	private HasChild hasChild;

	private Date createdDate;

	private Date updatedDate;

	public static CommentDetailResDTO of(CommentDetailVO commentDetailVO) {
		return CommentDetailResDTO.builder()
			.commentId(commentDetailVO.getCommentId())
			.parentId(commentDetailVO.getParentId())
			.postId(commentDetailVO.getPostId())
			.author(commentDetailVO.getAuthor())
			.content(commentDetailVO.getContent())
			.hasChild(commentDetailVO.getHasChild())
			.createdDate(commentDetailVO.getCreatedDate())
			.updatedDate(commentDetailVO.getUpdatedDate())
			.build();
	}
}
