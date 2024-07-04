package me.sisyphusj.community.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.security.CustomAccessDeniedHandler;
import me.sisyphusj.community.app.security.CustomAuthenticationEntryPoint;
import me.sisyphusj.community.app.utils.SessionUtil;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final String[] permittedUrls = {"/", "/auth/signup", "/auth/register", "/auth/login", "/auth/signin",
		"/WEB-INF/views/home.jsp", "/WEB-INF/views/signup.jsp", "/WEB-INF/views/login.jsp", "/WEB-INF/views/error/**",
		"/error"};

	private final CustomAuthenticationEntryPoint authEntryPoint;

	private final CustomAccessDeniedHandler accessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(
				authorize -> authorize
					.requestMatchers(permittedUrls).permitAll()
					.anyRequest()
					.authenticated()
			)

			.formLogin(
				form -> form
					.loginPage("/auth/login")
					.loginProcessingUrl("/auth/signin")
					.successHandler(authenticationSuccessHandler())
					.permitAll()
			)

			.logout(
				logout -> logout
					.logoutUrl("/auth/logout")
					.logoutSuccessUrl("/")
					.addLogoutHandler(logoutHandler())
			)

			.exceptionHandling(
				exceptionHandling -> exceptionHandling
					.accessDeniedHandler(accessDeniedHandler)
					.authenticationEntryPoint(authEntryPoint)
			);

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return (request, response, authentication) -> {
			SessionUtil.setLoginUserId(request.getSession(false), authentication.getName());
			response.sendRedirect("/");
		};
	}

	@Bean
	public LogoutHandler logoutHandler() {
		return (request, response, authentication) -> SessionUtil.sessionInvalidate(request.getSession(false));
	}

}
