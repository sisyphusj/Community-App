package me.sisyphusj.community.app.comment.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.image.domain.CommentImageResDTO;

@Getter
@Builder
public class CommentDetailResDTO {

	private long commentId; // 댓글 고유 ID

	private long userId; // 작성자 고유 ID

	private Long parentId; // 부모 댓글 고유 ID

	private long postId; // 게시글 고유 ID

	private String name; // 작성자 이름

	private String content; // 댓글 내용

	private Date createdAt; // 댓글 생성일

	private Date updatedAt; // 댓글 수정일

	private List<CommentImageResDTO> images; // 첨부 이미지 리스트

	private int likes; // 댓글 좋아요 개수

	private boolean hasLike; // 현재 사용자의 좋아요 여부

	public static CommentDetailResDTO of(CommentVO commentVO) {
		return CommentDetailResDTO.builder()
			.commentId(commentVO.getCommentId())
			.userId(commentVO.getUserId())
			.parentId(commentVO.getParentId())
			.postId(commentVO.getPostId())
			.name(commentVO.getName())
			.content(commentVO.getContent())
			.createdAt(commentVO.getCreatedAt())
			.updatedAt(commentVO.getUpdatedAt())
			.images(Optional.ofNullable(commentVO.getImages())
				.orElseGet(Collections::emptyList) // CommentVO.getImages()가 NULL일 경우 빈 리스트 생성 
				.stream()
				.map(CommentImageResDTO::of)
				.toList())
			.likes(commentVO.getLikes())
			.hasLike(commentVO.getHasLike() == 1)
			.build();
	}
}
