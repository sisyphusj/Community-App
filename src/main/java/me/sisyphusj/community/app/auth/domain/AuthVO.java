package me.sisyphusj.community.app.auth.domain;

import lombok.Getter;

@Getter
public class AuthVO {

	// 사용자 고유 ID
	private long userId;

	// 비밀번호
	private String password;

}
