package me.sisyphusj.community.app.comment.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sisyphusj.community.app.image.domain.ImageVO;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

	private Long commentId; // 댓글 고유 ID

	private Long parentId; // 부모 댓글 ID

	private Long userId; // 작성자 고유 ID

	private Long postId; // 게시글 고유 ID

	private String name; // 작성자 이름

	private String content; // 댓글 내용

	private Date createdAt; // 댓글 생성일

	private Date updatedAt; // 댓글 수정일

	private List<ImageVO> images; // 댓글 이미지 정보

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
