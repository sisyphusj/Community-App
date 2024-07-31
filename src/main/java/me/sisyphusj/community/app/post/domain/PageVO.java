package me.sisyphusj.community.app.post.domain;

import lombok.Builder;

@Builder
public class PageVO {

	private int amount; // 페이지 당 호출되는 레코드 개수

	private int offset; // 마지막으로 조회된 레코드 row

	private PageSortType sortType; // 정렬 타입

	private KeywordType keywordType; // 키워드 타입

	private String keyword; // 검색 키워드

	private BoardType boardType; // 게시판 타입

	private String category; // 게시판 카테고리

	public static PageVO of(PageReqDTO pageReqDTO) {
		return PageVO.builder()
			.amount(pageReqDTO.getRow())
			.offset((pageReqDTO.getPage() - 1) * pageReqDTO.getRow())
			.sortType(pageReqDTO.getSort())
			.keywordType(pageReqDTO.getKeywordType())
			.keyword(pageReqDTO.getKeyword())
			.category(pageReqDTO.getCategory())
			.build();
	}

	public void updateBoardType(BoardType boardType) {
		this.boardType = boardType;
	}
}
