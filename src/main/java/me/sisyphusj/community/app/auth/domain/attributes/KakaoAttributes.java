package me.sisyphusj.community.app.auth.domain.attributes;

import java.util.Map;

import me.sisyphusj.community.app.auth.domain.OAuthProvider;

public class KakaoAttributes extends OAuthAttributes {

	public KakaoAttributes(Map<String, Object> attributes, String nameAttributeKey) {
		super(attributes, nameAttributeKey, OAuthProvider.KAKAO);
	}

	@Override
	public String getName() {
		return (String)((Map)super.getAttributes().get("properties")).get("nickname");
	}

	@Override
	public String getUserIdentify() {
		return String.valueOf(super.getAttributes().get(getNameAttributeKey()));
	}
}
