package me.sisyphusj.community.app.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthInsertVO {

	// user Id
	private Long userId;

	// OAuth 제공자 ID
	private String username;

	// 사용자 성명
	private String name;

	// 제공자
	private OAuthProvider oAuthProvider;

	public static OAuthInsertVO of(OAuthDTO oAuthDTO) {
		return OAuthInsertVO.builder()
			.username(oAuthDTO.getUsername())
			.name(oAuthDTO.getName())
			.oAuthProvider(oAuthDTO.getOAuthProvider())
			.build();
	}
}
