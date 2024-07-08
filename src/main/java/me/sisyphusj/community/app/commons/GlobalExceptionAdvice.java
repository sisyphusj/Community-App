package me.sisyphusj.community.app.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.exception.AlertException;
import me.sisyphusj.community.app.commons.exception.AuthorizeException;
import me.sisyphusj.community.app.commons.exception.BlankInputException;
import me.sisyphusj.community.app.commons.exception.RedirectType;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

	/**
	 * Alert 예외 핸들러
	 */
	@ExceptionHandler(AlertException.class)
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView handleAlertException(AlertException e) {
		ModelAndView mav = new ModelAndView("error/alert");
		mav.addObject("message", e.getMessage());
		mav.addObject("redirectUrl", e.getRedirectType());
		return mav;
	}

	/**
	 * 인증 예외를 다루는 커스텀 핸들러
	 */
	@ExceptionHandler(AuthorizeException.class)
	public ModelAndView handleAuthorizeException(AuthorizeException e) {
		log.error("[AuthorizeException 발생] : {}", e.getMessage());
		ModelAndView mav = new ModelAndView("error/alert");
		mav.addObject("message", "로그인 실패");
		mav.addObject("redirectUrl", RedirectType.HOME);
		return mav;
	}

	@ExceptionHandler(BlankInputException.class)
	public ModelAndView handleBlankInputException(BlankInputException e) {
		log.error("[BlankInputException 발생]");
		ModelAndView mav = new ModelAndView("error/alert");
		mav.addObject("message", "입력값이 존재하지 않습니다. 다시 입력해주십시오.");
		mav.addObject("redirectUrl", RedirectType.NONE);
		return mav;
	}

	/**
	 * 지정한 예외를 제외한 나머지 예외를 처리하는 핸들러
	 */
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception e) {
		log.error("[Exception 발생] : ", e);
		return "error/500code";
	}

}
