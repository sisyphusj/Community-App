package me.sisyphusj.community.app.post.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.image.domain.ThumbnailImageResDTO;

@Getter
@Builder
public class PostDetailResDTO {

	private long userId; // 사용자 고유 ID

	private long postId; // 게시글 고유 ID

	private String name; // 작성자 이름

	private String title; // 제목

	private String content; // 본문

	private int views; // 조회 수

	private Date createdAt; // 생성일

	private Date updatedAt; // 최종 수정일

	private int likes; // 게시글 좋아요 수

	private boolean hasLike; // 현재 사용자의 게시글 좋아요 여부

	private ThumbnailImageResDTO thumbnail; // 썸네일 이미지 정보

	private String category; // 게시판 카테고리

	public static PostDetailResDTO of(PostVO postVO) {
		return PostDetailResDTO.builder()
			.userId(postVO.getUserId())
			.postId(postVO.getPostId())
			.name(postVO.getName())
			.title(postVO.getTitle())
			.content(postVO.getContent())
			.views(postVO.getViews())
			.createdAt(postVO.getCreatedAt())
			.updatedAt(postVO.getUpdatedAt())
			.likes(postVO.getLikes())
			.hasLike(postVO.getHasLike() == 1)
			.thumbnail(postVO.getThumbnail() == null ? null : ThumbnailImageResDTO.of(postVO.getThumbnail()))
			.category(postVO.getCategory())
			.build();
	}
}
