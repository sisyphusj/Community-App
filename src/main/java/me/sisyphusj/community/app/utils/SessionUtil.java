package me.sisyphusj.community.app.utils;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {

	private static final String LOGIN_USER_ID = "LOGIN_USER_ID";

	private SessionUtil() {
	}

	/**
	 * 세션 사용자 고유 ID 설정
	 */
	public static void setLoginUserId(HttpSession session, String id) {
		session.setAttribute(LOGIN_USER_ID, Integer.valueOf(id));
	}

	/**
	 * 세션 사용자 고유 ID 조회
	 */
	public static Integer getLoginUserId(HttpSession session) {
		if (session != null) {
			return (Integer)session.getAttribute(LOGIN_USER_ID);
		}
		return null;
	}

	/**
	 * 세션 무효화
	 */
	public static void sessionInvalidate(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
	}
}
