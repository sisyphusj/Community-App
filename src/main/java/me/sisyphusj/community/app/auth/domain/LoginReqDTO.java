package me.sisyphusj.community.app.auth.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
