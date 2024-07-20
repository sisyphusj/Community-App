package me.sisyphusj.community.app.comment.domain;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Getter
@Builder
public class CommentEditVO {

	private Long userId; // 사용자 고유 ID

	private Long postId; // 게시글 고유 ID

	private Long commentId; // 댓글 고유 ID

	private String content; // 댓글 내용

	public static CommentEditVO of(CommentEditReqDTO commentEditReqDTO) {
		return CommentEditVO.builder()
			.userId(SecurityUtil.getLoginUserId())
			.postId(commentEditReqDTO.getPostId())
			.commentId(commentEditReqDTO.getCommentId())
			.content(commentEditReqDTO.getContent())
			.build();
	}
}
