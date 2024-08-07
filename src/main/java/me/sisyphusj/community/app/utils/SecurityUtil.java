package me.sisyphusj.community.app.utils;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import me.sisyphusj.community.app.commons.exception.AuthorizeException;

public class SecurityUtil {

	private SecurityUtil() {
	}

	/**
	 * Authentication 에서 사용자 정보 추출
	 */
	public static long getLoginUserId() {
		Authentication authentication = getCurrentAuthentication();
		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetails userDetails) {
			return Integer.parseInt(userDetails.getUsername());
		} else if (principal instanceof DefaultOAuth2User user) {
			return Integer.parseInt(user.getName());
		} else {
			throw new AuthorizeException("인증된 사용자 정보를 찾을 수 없습니다.");
		}
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
			throw new AuthorizeException("인증정보가 존재하지 않습니다.");
		}
		return authentication;
	}

	/**
	 * 현재 사용자가 로그인된 사용자인지 확인
	 */
	public static boolean isLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !Objects.equals(authentication.getName(), "anonymousUser");
	}
}
