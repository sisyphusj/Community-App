package me.sisyphusj.community.app.auth.service;

import static me.sisyphusj.community.app.auth.domain.OAuthProvider.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.auth.domain.OAuthDTO;
import me.sisyphusj.community.app.auth.domain.OAuthProvider;
import me.sisyphusj.community.app.auth.domain.OAuthVO;
import me.sisyphusj.community.app.auth.mapper.AuthMapper;
import me.sisyphusj.community.app.commons.exception.CustomAuthenticationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final AuthMapper authMapper;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

		// 제공자가 발급한 고유 식별자 => username
		String userIdentifyName = (String)oAuth2User.getAttributes().get(userNameAttributeName);

		// 사용자의 프로필 이름
		String name = (String)oAuth2User.getAttributes().get("name");

		// OAuth 인증 정보가 담긴 DTO 생성
		OAuthDTO oAuthDTO = OAuthDTO.of(userIdentifyName, name, getProviderNameFromId(registrationId));

		// 최초 로그인 또는 로그인 시 DB의 PK인 userId를 반환
		String userId = String.valueOf(saveOrUpdate(oAuthDTO));

		// OAuth2User 에 userId 추가
		Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
		attributes.put("userId", userId);

		// 일반 사용자 권한 설정
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");

		// OAuth2User 객체 생성 및 반환
		return new DefaultOAuth2User(
			Collections.singleton(grantedAuthority),
			attributes,
			"userId");
	}

	private int saveOrUpdate(OAuthDTO oAuthDTO) {
		Optional<OAuthVO> oAuthVO = authMapper.selectOAuthByUsername(oAuthDTO.getUsername());
		return oAuthVO.map(OAuthVO::getUserId).orElseGet(() -> signupOAuth(oAuthDTO));
	}

	private OAuthProvider getProviderNameFromId(String registrationId) {
		return switch (registrationId.toLowerCase()) {
			case "kakao" -> KAKAO;
			case "google" -> GOOGLE;
			case "naver" -> NAVER;
			default -> throw new CustomAuthenticationException("알 수 없는 제공자입니다.: " + registrationId);
		};
	}

	private int signupOAuth(OAuthDTO oAuthDTO) {
		return authMapper.insertOAuth(OAuthVO.of(oAuthDTO));
	}
}
