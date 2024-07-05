package me.sisyphusj.community.app.commons.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

	/**
	 * Alert 예외 핸들러
	 */
	@ExceptionHandler(AlertException.class)
	public ModelAndView handleAlertException(AlertException e) {
		log.error("error : {}", e.getReason());
		ModelAndView mav = new ModelAndView("error/alert");
		mav.addObject("message", e.getMessage());
		mav.addObject("redirectUrl", e.getRedirectType());
		return mav;
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
