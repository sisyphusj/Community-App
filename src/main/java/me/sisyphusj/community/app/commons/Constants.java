package me.sisyphusj.community.app.commons;

import java.util.List;

public class Constants {

	private Constants() {
	}

	// alert message
	public static final String MESSAGE = "message";

	// alert location Url
	public static final String LOCATION_URL = "locationUrl";

	// 커스텀 지정 Url
	public static final String CUSTOM_URL = "customUrl";

	// 사용자 알람 jsp Url
	public static final String MAV_ALERT = "error/alert";

	// 500 예외 페이지
	public static final String MAV_500 = "error/500code";

	// 400 예외 페이지
	public static final String MAV_400 = "error/400code";

	// Authorization default roll
	public static final String USER = "USER";

	// 페이지 버튼 개수
	public static final int PAGE_SIZE = 5;

	// 허용된 이미지 파일 확장자
	public static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png");

	// 썸네일 이미지 리사이징 너비
	public static final int THUMBNAIL_WIDTH = 150;

	// 썸네일 이미지 리사이징 폭
	public static final int THUMBNAIL_HEIGHT = 150;

}
