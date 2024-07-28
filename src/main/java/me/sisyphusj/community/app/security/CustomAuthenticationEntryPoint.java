package me.sisyphusj.community.app.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.LocationUrl;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException, IOException {
		log.error("[인증 에러] : ", authException);
		
		request.setAttribute("message", "로그인이 필요합니다.");
		request.setAttribute("locationUrl", LocationUrl.HOME);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error/alert.jsp");
		dispatcher.forward(request, response);
	}
}
