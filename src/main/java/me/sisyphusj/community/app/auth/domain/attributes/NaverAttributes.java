package me.sisyphusj.community.app.auth.domain.attributes;

import java.util.Map;

import me.sisyphusj.community.app.auth.domain.OAuthProvider;

public class NaverAttributes extends OAuthAttributes {

	public NaverAttributes(Map<String, Object> attributes, String nameAttributeKey) {
		super(attributes, nameAttributeKey, OAuthProvider.NAVER);
	}

	@Override
	public String getName() {
		return (String)super.getAttributes().get("name");
	}

	@Override
	public String getUserIdentify() {
		return (String)super.getAttributes().get("id");
	}
}
