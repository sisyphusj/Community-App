package me.sisyphusj.community.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.auth.service.CustomOAuth2UserService;
import me.sisyphusj.community.app.security.CustomAccessDeniedHandler;
import me.sisyphusj.community.app.security.CustomAuthenticationEntryPoint;
import me.sisyphusj.community.app.security.CustomAuthenticationFailureHandler;
import me.sisyphusj.community.app.security.CustomAuthenticationSuccessHandler;
import me.sisyphusj.community.app.security.CustomLogoutHandler;
import me.sisyphusj.community.app.security.CustomOAuthAuthenticationFailureHandler;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final String[] permittedUrls = {"/", "/auth/**", "/WEB-INF/views/**", "/auth/oauth2/code/**", "/community/**", "favicon.ico", "/error"};

	private final CustomAuthenticationEntryPoint authEntryPoint;

	private final CustomAccessDeniedHandler accessDeniedHandler;

	private final CustomAuthenticationFailureHandler failureHandler;

	private final CustomOAuthAuthenticationFailureHandler oAuthFailureHandler;

	private final CustomAuthenticationSuccessHandler successHandler;

	private final CustomLogoutHandler customLogoutHandler;

	private final CustomOAuth2UserService customOAuth2UserService;

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
					.anyRequest().authenticated()
			)

			.formLogin(
				form -> form
					.loginPage("/auth/login")
					.loginProcessingUrl("/auth/signin")
					.successHandler(successHandler)
					.failureHandler(failureHandler)
					.permitAll()
			)

			.oauth2Login(oauth2Login -> oauth2Login
				.loginPage("/auth/login")
				.redirectionEndpoint(
					redirect -> redirect
						.baseUri("/auth/oauth2/code/**")
				)
				.userInfoEndpoint(userInfo -> userInfo
					.userService(customOAuth2UserService)
				)
				.successHandler(successHandler)
				.failureHandler(oAuthFailureHandler)
			)

			.logout(
				logout -> logout
					.logoutUrl("/auth/logout")
					.logoutSuccessUrl("/")
					.addLogoutHandler(customLogoutHandler)
			)

			.exceptionHandling(
				exceptionHandling -> exceptionHandling
					.accessDeniedHandler(accessDeniedHandler)
					.authenticationEntryPoint(authEntryPoint)
			);

		return http.build();
	}

}
