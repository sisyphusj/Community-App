package me.sisyphusj.community.app.auth.domain;

import lombok.Getter;
import me.sisyphusj.community.app.user.domain.OAuthProvider;

@Getter
public class AuthVO {

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

}
