package me.sisyphusj.community.app.commons.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AuthorizeException extends RuntimeException {

	private final int status;

	private final String message;

	private final RedirectType redirectType;

	public AuthorizeException(String message, HttpStatus status, RedirectType redirectType) {
		this.status = status.value();
		this.message = message;
		this.redirectType = redirectType;
	}

	public static AuthorizeException sessionNotAvailable() {
		return AuthorizeException.builder()
			.status(401)
			.message("세션이 만료되었습니다. 다시 로그인 해주십시오.")
			.redirectType(RedirectType.HOME)
			.build();

	}

}
