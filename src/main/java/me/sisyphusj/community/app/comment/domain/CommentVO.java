package me.sisyphusj.community.app.comment.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Getter
@Builder
public class CommentVO {

	private Long commentId; // 댓글 고유 ID

	private Long parentId; // 부모 댓글 ID

	private Long userId; // 작성자 고유 ID

	private Long postId; // 게시글 고유 ID

	private String author; // 작성자 이름

	private String content; // 댓글 내용

	private Date createdDate; // 댓글 생성일

	private Date updatedDate; // 댓글 수정일

	public static CommentVO of(CommentReqDTO commentReqDTO) {
		return CommentVO.builder()
			.userId(SecurityUtil.getLoginUserId())
			.postId(commentReqDTO.getPostId())
			.parentId(commentReqDTO.getParentId())
			.content(commentReqDTO.getContent())
			.build();
	}

	public static CommentVO of(CommentEditReqDTO commentEditReqDTO) {
		return CommentVO.builder()
			.userId(SecurityUtil.getLoginUserId())
			.postId(commentEditReqDTO.getPostId())
			.commentId(commentEditReqDTO.getCommentId())
			.content(commentEditReqDTO.getContent())
			.build();
	}
}
