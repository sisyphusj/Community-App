package me.sisyphusj.community.app.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import me.sisyphusj.community.app.commons.exception.AuthorizeException;

public class SessionUtil {

	private static final String LOGIN_USER_ID = "LOGIN_USER_ID";

	private SessionUtil() {
	}

	private static HttpSession getCurrentSession() {
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
	}

	/**
	 * 세션 사용자 고유 ID 설정
	 */
	public static void setLoginUserId(String userId) {
		HttpSession session = getCurrentSession();

		if (session == null) {
			throw AuthorizeException.sessionNotAvailable();
		}

		session.setAttribute(LOGIN_USER_ID, Integer.valueOf(userId));
	}

	/**
	 * 세션 사용자 고유 ID 조회
	 */
	public static Integer getLoginUserId() {
		HttpSession session = getCurrentSession();

		if (session == null || session.getAttribute(LOGIN_USER_ID) == null) {
			throw AuthorizeException.sessionNotAvailable();
		}

		return (Integer)session.getAttribute(LOGIN_USER_ID);
	}

	/**
	 * 세션 무효화
	 */
	public static void sessionInvalidate() {
		HttpSession session = getCurrentSession();

		if (session != null) {
			session.invalidate();
		}
	}

}
