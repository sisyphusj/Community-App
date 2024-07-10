package me.sisyphusj.community.app.post.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostThumbnailResDTO {

	// 게시글 고유 ID
	private String postId;

	// 작성자
	private String author;

	// 제목
	private String title;

	// 생성 시간
	private LocalDateTime createdDate;

	public static PostThumbnailResDTO of(PostThumbnailVO postVO) {
		return PostThumbnailResDTO.builder()
			.postId(postVO.getPostId())
			.author(postVO.getAuthor())
			.title(postVO.getTitle())
			.createdDate(postVO.getCreatedDate())
			.build();
	}
}
