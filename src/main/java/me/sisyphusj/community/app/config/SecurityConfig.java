package me.sisyphusj.community.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpSession;
import me.sisyphusj.community.app.auth.SessionConst;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final String[] permittedUrls = {"/", "/user/signup", "/user/register", "/auth/login", "/auth/signin",
		"/WEB-INF/views/home.jsp",
		"/WEB-INF/views/signup.jsp", "/WEB-INF/views/login.jsp", "/error"};

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
			);

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {

		return (request, response, authentication) -> {

			HttpSession session = request.getSession();
			session.setAttribute(SessionConst.LOGIN_USER, authentication.getName());
			response.sendRedirect("/");

		};
	}

}
