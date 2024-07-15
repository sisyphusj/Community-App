package me.sisyphusj.community.app.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.sisyphusj.community.app.commons.RedirectType;

@Component
public class CustomLoginFilter extends OncePerRequestFilter {

	private static final List<String> LOGIN_URLS = Arrays.asList("/auth/login", "/auth/signin");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 사용자가 이미 인증된 상태이고, 로그인 페이지 또는 로그인 프로세스 URL에 접근 시 차단
		if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser") && LOGIN_URLS.contains(request.getRequestURI())) {
			request.setAttribute("message", "이미 로그인한 사용자입니다. ");
			request.setAttribute("redirectUrl", RedirectType.HOME);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/error/alert.jsp");
			dispatcher.forward(request, response);
			return;
		}

		filterChain.doFilter(request, response);
	}
}