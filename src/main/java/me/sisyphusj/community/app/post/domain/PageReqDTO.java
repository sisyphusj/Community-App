package me.sisyphusj.community.app.post.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageReqDTO {

	@NotNull
	private Integer page; // 현재 페이지

	@NotNull
	private PageSortType sort; // 정렬 타입

	private KeywordType keywordType; // 검색 타입

	private String keyword; // 검색 키워드

	@NotNull
	private Integer row; // 페이지 당 게시글 개수

}
