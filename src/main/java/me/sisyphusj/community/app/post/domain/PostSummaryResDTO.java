package me.sisyphusj.community.app.post.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.image.domain.ThumbnailImageResDTO;

@Builder
@Getter
public class PostSummaryResDTO {

	private long postId; // 게시글 고유 ID

	private String name; // 작성자

	private String title; // 제목

	private int views; // 조회 수

	private Date createdAt; // 생성 시간

	private Date updatedAt; // 최종 수정일

	private int likes; // 게시글 좋아요 수

	private ThumbnailImageResDTO thumbnail; // 썸네일 이미지 정보

	public static PostSummaryResDTO of(PostVO postVO) {
		return PostSummaryResDTO.builder()
			.postId(postVO.getPostId())
			.name(postVO.getName())
			.title(postVO.getTitle())
			.views(postVO.getViews())
			.createdAt(postVO.getCreatedAt())
			.updatedAt(postVO.getUpdatedAt())
			.likes(postVO.getLikes())
			.thumbnail(postVO.getThumbnail() == null ? null : ThumbnailImageResDTO.of(postVO.getThumbnail()))
			.build();
	}
}
