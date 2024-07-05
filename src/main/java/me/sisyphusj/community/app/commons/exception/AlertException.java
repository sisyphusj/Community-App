package me.sisyphusj.community.app.commons.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlertException extends RuntimeException {

	private int status;

	private String message;

	private String reason;

	private RedirectType redirectType;

	public AlertException(String message, HttpStatus status, RedirectType redirectType) {
		this.status = status.value();
		this.message = message;
		this.redirectType = redirectType;
	}

	public AlertException(String message, RedirectType redirectType) {
		this.status = 500;
		this.message = message;
		this.redirectType = redirectType;
	}

}
