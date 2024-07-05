package me.sisyphusj.community.app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.sisyphusj.community.app.utils.SessionUtil;

@Component
public class CustomLogoutHandler implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		SessionUtil.sessionInvalidate();
	}
}
