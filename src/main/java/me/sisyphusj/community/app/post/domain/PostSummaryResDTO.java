package me.sisyphusj.community.app.post.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostSummaryResDTO {

	// 게시글 고유 ID
	private String postId;

	// 작성자
	private String author;

	// 제목
	private String title;

	// 생성 시간
	private Date createdDate;

	public static PostSummaryResDTO of(PostSummaryVO postVO) {
		return PostSummaryResDTO.builder()
			.postId(postVO.getPostId())
			.author(postVO.getAuthor())
			.title(postVO.getTitle())
			.createdDate(postVO.getCreatedDate())
			.build();
	}
}
