package me.sisyphusj.community.app.post.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostSummaryVO {

	// 게시글 고유 ID
	private long postId;

	// 작성자
	private String author;

	// 제목
	private String title;

	// 조회 수
	private int views;

	// 생성 시간
	private Date createdDate;

}
