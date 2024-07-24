package me.sisyphusj.community.app.post.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailResDTO {

	private long userId; // 사용자 고유 ID

	private long postId; // 게시글 고유 ID

	private String name; // 작성자 이름

	private String title; // 제목

	private String content; // 본문

	private HasImage hasImage; // 이미지 첨부 여부

	private int views; // 조회 수

	private Date createdAt; // 생성일

	private Date updatedAt; // 최종 수정일

	public static PostDetailResDTO of(PostVO postDetailVO) {
		return PostDetailResDTO.builder()
			.userId(postDetailVO.getUserId())
			.postId(postDetailVO.getPostId())
			.name(postDetailVO.getName())
			.title(postDetailVO.getTitle())
			.content(postDetailVO.getContent())
			.hasImage(postDetailVO.getHasImage())
			.views(postDetailVO.getViews())
			.createdAt(postDetailVO.getCreatedAt())
			.updatedAt(postDetailVO.getUpdatedAt())
			.build();
	}
}
