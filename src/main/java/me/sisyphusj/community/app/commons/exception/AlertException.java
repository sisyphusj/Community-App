package me.sisyphusj.community.app.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlertException extends RuntimeException {

	private int status;

	private String message;

	private String reason;

	public AlertException(int status, String message) {
		this.status = status;
		this.message = message;
	}

}
