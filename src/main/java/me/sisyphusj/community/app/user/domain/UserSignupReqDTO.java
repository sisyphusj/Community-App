package me.sisyphusj.community.app.user.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupReqDTO {

	// 사용자 로그인 아이디
	@NotBlank
	private String username;

	// 사용자 이름
	@NotBlank
	private String name;

	// 비밀번호
	private String password;

	public void updatePassword(String password) {
		this.password = password;
	}

}
