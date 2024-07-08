package me.sisyphusj.community.app.auth.domain.attributes;

import java.util.Map;

import me.sisyphusj.community.app.auth.domain.OAuthProvider;

public class NaverAttributes extends OAuthAttributes {

	public NaverAttributes(Map<String, Object> attributes, String nameAttributeKey) {
		super(attributes, nameAttributeKey, OAuthProvider.NAVER, (String)attributes.get("name"), (String)attributes.get("id"));
	}
}
