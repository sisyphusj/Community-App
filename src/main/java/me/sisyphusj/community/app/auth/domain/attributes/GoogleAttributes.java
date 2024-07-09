package me.sisyphusj.community.app.auth.domain.attributes;

import java.util.Map;

import me.sisyphusj.community.app.auth.domain.OAuthProvider;

public class GoogleAttributes extends OAuthAttributes {

	public GoogleAttributes(Map<String, Object> attributes, String nameAttributeKey) {
		super(attributes, nameAttributeKey, OAuthProvider.GOOGLE);
	}

	@Override
	public String getName() {
		return (String)super.getAttributes().get("name");
	}

	@Override
	public String getUserIdentify() {
		return (String)super.getAttributes().get(getNameAttributeKey());
	}
}
