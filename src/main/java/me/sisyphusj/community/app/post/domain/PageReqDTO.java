package me.sisyphusj.community.app.post.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PageReqDTO {

	@NotNull
	private Integer page; // 현재 페이지

	@NotNull
	private PageSortType sort; // 정렬 타입

	private KeywordType keywordType; // 검색 타입

	private String keyword; // 검색 키워드

	@NotNull
	private Integer row; // 페이지 당 게시글 개수

	/**
	 * 전체 생성자에서 page, sort, row 에 대한 기본값 지정
	 */
	public PageReqDTO(Integer page, PageSortType sort, KeywordType keywordType, String keyword, Integer row) {
		this.page = page == null ? 1 : page;
		this.sort = sort == null ? PageSortType.DATE : sort;
		this.keywordType = keywordType;
		this.keyword = keyword;
		this.row = row == null ? 10 : row;
	}
}
