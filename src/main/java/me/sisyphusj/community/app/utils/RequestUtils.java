package me.sisyphusj.community.app.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {

	private RequestUtils() {
	}

	public static HttpServletRequest getCurrReq() {
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	}

}
