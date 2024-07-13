package me.sisyphusj.community.app.commons;

import static me.sisyphusj.community.app.commons.Constants.*;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.exception.AlertException;
import me.sisyphusj.community.app.commons.exception.AuthorizeException;
import me.sisyphusj.community.app.commons.exception.BlankInputException;
import me.sisyphusj.community.app.commons.exception.PostNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

	/**
	 * Alert 예외 핸들러
	 */
	@ExceptionHandler(AlertException.class)
	public ModelAndView handleAlertException(AlertException e, HttpServletResponse response) {
		if (e.getStatus() == null) {
			response.setStatus(HttpStatus.OK.value());
		} else {
			response.setStatus(e.getStatus().value());
		}

		ModelAndView mav = new ModelAndView(MAV_ALERT);
		mav.addObject(MESSAGE, e.getMessage());
		mav.addObject(REDIRECT_URL, e.getRedirectType());
		return mav;
	}

	/**
	 * 인증 예외를 다루는 커스텀 핸들러
	 */
	@ExceptionHandler(AuthorizeException.class)
	public ModelAndView handleAuthorizeException(AuthorizeException e) {
		log.error("[AuthorizeException 발생] : {}", e.getMessage());
		ModelAndView mav = new ModelAndView(MAV_ALERT);
		mav.addObject(MESSAGE, "로그인이 필요합니다.");
		mav.addObject(REDIRECT_URL, RedirectType.HOME);
		return mav;
	}

	/**
	 * 파라미터에 값이 공백인 경우를 처리하는 핸들러
	 */
	@ExceptionHandler(BlankInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView handleBlankInputException(BlankInputException e) {
		log.error("[BlankInputException 발생]");
		ModelAndView mav = new ModelAndView(MAV_ALERT);
		mav.addObject(MESSAGE, "입력값이 존재하지 않습니다. 다시 입력해주십시오.");
		mav.addObject(REDIRECT_URL, RedirectType.NONE);
		return mav;
	}

	/**
	 * 게시글 조회에 실패한 경우를 처리하는 핸들러
	 */
	@ExceptionHandler(PostNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handelPostNotFoundException(PostNotFoundException e) {
		log.error("[PostNotFoundException 발생]");
		ModelAndView mav = new ModelAndView(MAV_ALERT);
		mav.addObject(MESSAGE, "요청하신 게시글을 찾을 수 없습니다.");
		mav.addObject(REDIRECT_URL, RedirectType.BACK);
		return mav;
	}

	/**
	 * 파일 업로드 실패한 경우를 처리하는 핸들러
	 */
	@ExceptionHandler(FileUploadException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleFileUploadException(FileUploadException e) {
		log.error("[FileUploadException 발생]");
		ModelAndView mav = new ModelAndView(MAV_ALERT);
		mav.addObject(MESSAGE, "이미지 업로드에 실패하였습니다.");
		mav.addObject(REDIRECT_URL, RedirectType.NONE);
		return mav;
	}

	/**
	 * 메소드의 잘못된 인수로 인한 예외를 처리하는 핸들러
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleIllegalArgumentException(IllegalArgumentException e) {
		log.error("[IllegalArgumentException 발생] : {}", e.getMessage(), e);
		return new ModelAndView(MAV_400);
	}

	/**
	 * 지정한 예외를 제외한 나머지 예외를 처리하는 핸들러
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception e) {
		log.error("[Exception 발생] : ", e);
		return new ModelAndView(MAV_500);
	}

}
