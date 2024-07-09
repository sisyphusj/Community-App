package me.sisyphusj.community.app.commons;

import java.util.Map;

import me.sisyphusj.community.app.auth.domain.attributes.GoogleAttributes;
import me.sisyphusj.community.app.auth.domain.attributes.KakaoAttributes;
import me.sisyphusj.community.app.auth.domain.attributes.NaverAttributes;
import me.sisyphusj.community.app.auth.domain.attributes.OAuthAttributes;
import me.sisyphusj.community.app.commons.exception.CustomAuthenticationException;

public class OAuthAttributesFactory {

	private OAuthAttributesFactory() {
	}

	public static OAuthAttributes getAttributeByProvider(Map<String, Object> attributes, String nameAttributeKey, String registrationId) {
		return switch (registrationId.toLowerCase()) {
			case "kakao" -> new KakaoAttributes(attributes, nameAttributeKey);
			case "google" -> new GoogleAttributes(attributes, nameAttributeKey);
			case "naver" -> new NaverAttributes((Map)attributes.get("response"), nameAttributeKey);
			default -> throw new CustomAuthenticationException("알 수 없는 제공자입니다.: " + registrationId);
		};
	}
}
