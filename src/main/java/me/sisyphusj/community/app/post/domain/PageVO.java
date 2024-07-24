package me.sisyphusj.community.app.post.domain;

import static me.sisyphusj.community.app.commons.Constants.*;

import lombok.Builder;

@Builder
public class PageVO {

	private int amount; // 페이지 당 호출되는 레코드 개수

	private int offset; // 마지막으로 조회된 레코드 row

	PageSortType sortType; // 정렬 타입

	String keyword; // 검색 키워드

	public static PageVO of(PageReqDTO pageReqDTO) {
		return PageVO.builder()
			.amount(ROW_SIZE_PER_PAGE)
			.offset((pageReqDTO.getPage() - 1) * ROW_SIZE_PER_PAGE)
			.sortType(pageReqDTO.getSort())
			.keyword(pageReqDTO.getKeyword())
			.build();
	}
}
