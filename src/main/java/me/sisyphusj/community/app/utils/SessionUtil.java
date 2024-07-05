package me.sisyphusj.community.app.utils;

import jakarta.servlet.http.HttpSession;
import me.sisyphusj.community.app.commons.exception.AlertException;
import me.sisyphusj.community.app.commons.exception.RedirectType;

public class SessionUtil {

	private static final String LOGIN_USER_ID = "LOGIN_USER_ID";

	private SessionUtil() {
	}

	/**
	 * 세션 사용자 고유 ID 설정
	 */
	public static void setLoginUserId(String userId) {
		HttpSession session = RequestUtils.getCurrReq().getSession(false);

		if (session == null) {
			throw new AlertException("세션이 만료되었습니다. 다시 로그인 해주십시오", RedirectType.HOME);
		}

		session.setAttribute(LOGIN_USER_ID, Integer.valueOf(userId));
	}

	/**
	 * 세션 사용자 고유 ID 조회
	 */
	public static Integer getLoginUserId() {
		HttpSession session = RequestUtils.getCurrReq().getSession(false);

		if (session == null) {
			throw new AlertException("세션이 만료되었습니다. 다시 로그인 해주십시오", RedirectType.HOME);
		}

		return (Integer)session.getAttribute(LOGIN_USER_ID);
	}

	/**
	 * 세션 무효화
	 */
	public static void sessionInvalidate() {
		HttpSession session = RequestUtils.getCurrReq().getSession(false);

		if (session != null) {
			session.invalidate();
		}
	}
}
