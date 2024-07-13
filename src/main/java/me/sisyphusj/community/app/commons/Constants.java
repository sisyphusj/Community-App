package me.sisyphusj.community.app.commons;

import java.util.List;

public class Constants {

	private Constants() {
	}

	// alert message
	public static final String MESSAGE = "message";

	// alert redirect way
	public static final String REDIRECT_URL = "redirectUrl";

	public static final String MAV_ALERT = "error/alert";

	public static final String MAV_500 = "error/500code";

	public static final String MAV_400 = "error/400code";

	// Authorization default roll
	public static final String USER = "USER";

	// 페이지 버튼 개수
	public static final int PAGE_SIZE = 5;

	public static final int ROW_SIZE_PER_PAGE = 10;

	// 허용된 이미지 파일 확장자
	public static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png");

}
