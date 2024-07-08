package me.sisyphusj.community.app.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OAuthDTO {

	// OAuth 제공자 ID
	private String username;

	// 사용자 성명
	private String name;

	// 제공자
	private OAuthProvider oAuthProvider;
	
}
