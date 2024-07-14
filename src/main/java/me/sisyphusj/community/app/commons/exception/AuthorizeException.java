package me.sisyphusj.community.app.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizeException extends RuntimeException {
	private final String message;
}
