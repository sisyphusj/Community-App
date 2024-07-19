package me.sisyphusj.community.app.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.LocationUrl;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.error("[폼 로그인 중 에러] : ", exception);

		request.setAttribute("message", "로그인에 실패했습니다. 다시 시도 해주세요.");
		request.setAttribute("redirectUrl", LocationUrl.HOME);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error/alert.jsp");
		dispatcher.forward(request, response);

	}
}
