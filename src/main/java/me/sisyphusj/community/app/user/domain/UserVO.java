package me.sisyphusj.community.app.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserVO {

	// 사용자 고유 ID
	private int userId;

	// 사용자 로그인 아이디 or OAuth 제공자 ID
	private String username;

	// 사용자 이름
	private String name;

	// 비밀번호
	private String password;

	// OAuth 제공자 명
	private OAuthProvider provider;

	public static UserVO of(UserSignupReqDTO userSignupReqDTO) {

		return UserVO.builder()
			.username(userSignupReqDTO.getUsername())
			.name(userSignupReqDTO.getName())
			.password(userSignupReqDTO.getPassword())
			.build();
	}

}
