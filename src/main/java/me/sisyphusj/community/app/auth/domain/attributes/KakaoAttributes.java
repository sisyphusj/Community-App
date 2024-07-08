package me.sisyphusj.community.app.auth.domain.attributes;

import java.util.Map;

import me.sisyphusj.community.app.auth.domain.OAuthProvider;

public class KakaoAttributes extends OAuthAttributes {

	public KakaoAttributes(Map<String, Object> attributes, String nameAttributeKey) {
		super(attributes, nameAttributeKey, OAuthProvider.KAKAO, (String)((Map)attributes.get("properties")).get("nickname"), String.valueOf(attributes.get(nameAttributeKey)));
	}
}
