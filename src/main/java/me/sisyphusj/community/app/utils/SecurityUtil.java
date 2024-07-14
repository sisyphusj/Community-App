package me.sisyphusj.community.app.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import me.sisyphusj.community.app.commons.exception.AuthorizeException;

public class SecurityUtil {

	private SecurityUtil() {
	}

	/**
	 * Authentication 에서 사용자 정보 추출
	 */
	public static String getLoginUserId() {
		Authentication authentication = getCurrentAuthentication();
		Object principal = authentication.getPrincipal();

		if (!(principal instanceof UserDetails)) {
			throw new AuthorizeException();
		}

		return ((UserDetails)principal).getUsername();
	}

	/**
	 * Spring Security 컨텍스트 클리어
	 */
	public static void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	/**
	 * SecurityContextHolder 에 저장된 Authentication 객체 반환
	 */
	private static Authentication getCurrentAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new AuthorizeException();
		}
		return authentication;
	}
}
