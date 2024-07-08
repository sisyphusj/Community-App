package me.sisyphusj.community.app.auth.domain.attributes;

import java.util.Map;

import me.sisyphusj.community.app.auth.domain.OAuthProvider;

public class GoogleAttributes extends OAuthAttributes {

	public GoogleAttributes(Map<String, Object> attributes, String nameAttributeKey) {
		super(attributes, nameAttributeKey, OAuthProvider.GOOGLE, (String)attributes.get("name"), (String)attributes.get(nameAttributeKey));
	}
}
