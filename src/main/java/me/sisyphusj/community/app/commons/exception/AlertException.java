package me.sisyphusj.community.app.commons.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AlertException extends RuntimeException {

	private final HttpStatus status;

	private final String message;

	private final RedirectType redirectType;

	public static AlertException of500(String message, RedirectType redirectType) {
		return AlertException.builder()
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.message(message)
			.redirectType(redirectType)
			.build();
	}

	public static AlertException of400(String message, RedirectType redirectType) {
		return AlertException.builder()
			.status(HttpStatus.BAD_REQUEST)
			.message(message)
			.redirectType(redirectType)
			.build();
	}

}
