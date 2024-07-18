package me.sisyphusj.community.app.auth.domain;

import lombok.Getter;

@Getter
public class OAuthSelectVO {

	// user Id
	private long userId;

	// OAuth 제공자 ID
	private String username;

	// 사용자 성명
	private String name;

	// 제공자
	private OAuthProvider oAuthProvider;

}
