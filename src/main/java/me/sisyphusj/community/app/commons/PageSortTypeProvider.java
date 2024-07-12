package me.sisyphusj.community.app.commons;

import me.sisyphusj.community.app.post.domain.PageSortType;

public class PageSortTypeProvider {

	private PageSortTypeProvider() {
	}

	public static PageSortType getPageSortType(String sort) {
		return switch (sort) {
			case "date" -> PageSortType.DATE;
			case "views" -> PageSortType.VIEWS;
			default -> throw new IllegalArgumentException("잘못된 PageSortType" + sort);
		};
	}
}
