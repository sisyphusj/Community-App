package me.sisyphusj.community.app.post.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PageReqDTO {

	@NotNull
	private final Integer page; // 현재 페이지

	@NotNull
	private final PageSortType sort; // 정렬 타입

	private final KeywordType keywordType; // 검색 타입

	private final String keyword; // 검색 키워드

	@NotNull
	private final Integer row; // 페이지 당 게시글 개수

	@NotNull
	private final String category; // 게시글 카테고리

	/**
	 * 전체 생성자에서 page, sort, row 에 대한 기본값 지정
	 */
	public PageReqDTO(Integer page, PageSortType sort, KeywordType keywordType, String keyword, Integer row, String category) {
		this.page = page == null ? 1 : page;
		this.sort = sort == null ? PageSortType.DATE : sort;
		this.keywordType = keywordType;
		this.keyword = keyword;
		this.row = row == null ? 10 : row;
		this.category = category == null ? "ALL" : category;
	}
}
