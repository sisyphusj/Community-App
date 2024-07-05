package me.sisyphusj.community.app.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws ServletException, IOException {
		request.setAttribute("message", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error/alert.jsp");
		dispatcher.forward(request, response);
	}
}
