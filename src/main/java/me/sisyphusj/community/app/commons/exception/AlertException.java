package me.sisyphusj.community.app.commons.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AlertException extends RuntimeException {

	private final int status;

	private final String message;

	private final RedirectType redirectType;

	public AlertException(String message, HttpStatus status, RedirectType redirectType) {
		this.status = status.value();
		this.message = message;
		this.redirectType = redirectType;
	}

	public static AlertException of500(String message, RedirectType redirectType) {
		return AlertException.builder()
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.message(message)
			.redirectType(redirectType)
			.build();
	}

	public static AlertException of400(String message, RedirectType redirectType) {
		return AlertException.builder()
			.status(HttpStatus.BAD_REQUEST.value())
			.message(message)
			.redirectType(redirectType)
			.build();
	}

}
