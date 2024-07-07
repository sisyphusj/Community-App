package me.sisyphusj.community.app.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthDTO {

	// OAuth 제공자 ID
	private String username;

	// 사용자 성명
	private String name;

	// 제공자
	private OAuthProvider oAuthProvider;

	public static OAuthDTO of(String username, String name, OAuthProvider oAuthProvider) {
		return OAuthDTO.builder()
			.username(username)
			.name(name)
			.oAuthProvider(oAuthProvider)
			.build();
	}
}
