package me.sisyphusj.community.app.commons.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

	/**
	 * Alert 예외 핸들러
	 */
	@ExceptionHandler(AlertException.class)
	public String handleAlertException(Model model, AlertException e) {
		log.error("error : {}", e.getReason());
		model.addAttribute("message", e.getMessage());
		model.addAttribute("redirectUrl", "back");
		return "error/alert";
	}

	/**
	 * 인증 예외 핸들러
	 */
	@ExceptionHandler(AuthenticationException.class)
	public String handleAuthenticationException(Model model, AuthenticationException e) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.error("error : {}", authentication, e);
		model.addAttribute("message", "세션이 만료되었습니다. \n다시 로그인 해주세요");
		return "error/alert";
	}

	/**
	 * 권한 예외 핸들러
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDeniedException(Model model, AccessDeniedException e) {
		log.error("error : ", e);
		model.addAttribute("message", "세션이 만료되었습니다. \n다시 로그인 해주세요");
		return "error/alert";
	}

	/**
	 * 인증 실패 예외 핸들러
	 */
	@ExceptionHandler(InsufficientAuthenticationException.class)
	public String handleInsufficientAuthenticationException(Model model, InsufficientAuthenticationException e) {
		log.error("error : ", e);
		model.addAttribute("message", "세션이 만료되었습니다. \n다시 로그인 해주세요");
		return "error/alert";
	}

	/**
	 * 지정한 예외를 제외한 나머지 예외를 처리하는 핸들러
	 */
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception e) {
		log.error("error : ", e);
		return "error/500code";
	}

}
