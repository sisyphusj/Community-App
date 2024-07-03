package me.sisyphusj.community.app.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupVO {

	// 사용자 로그인 아이디
	private String username;

	// 사용자 이름
	private String name;

	// 비밀번호
	private String password;

	public static SignupVO of(SignupReqDTO signupReqDTO) {
		return SignupVO.builder()
			.username(signupReqDTO.getUsername())
			.name(signupReqDTO.getName())
			.password(signupReqDTO.getPassword())
			.build();
	}
}
