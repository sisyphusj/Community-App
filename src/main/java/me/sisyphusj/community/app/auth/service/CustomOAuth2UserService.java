package me.sisyphusj.community.app.auth.service;

import static me.sisyphusj.community.app.commons.Constants.*;

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
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.auth.domain.OAuthDTO;
import me.sisyphusj.community.app.auth.domain.OAuthVO;
import me.sisyphusj.community.app.auth.domain.attributes.GoogleAttributes;
import me.sisyphusj.community.app.auth.domain.attributes.KakaoAttributes;
import me.sisyphusj.community.app.auth.domain.attributes.NaverAttributes;
import me.sisyphusj.community.app.auth.domain.attributes.OAuthAttributes;
import me.sisyphusj.community.app.auth.mapper.AuthMapper;
import me.sisyphusj.community.app.commons.exception.CustomAuthenticationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final AuthMapper authMapper;

	@Override
	@Transactional
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

		// 수정 가능한 attributes 로 변경
		Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

		// 모든 OAuth 제공자의 응답을 수용
		OAuthAttributes oAuthAttributes = getAttributeByProvider(attributes, userNameAttributeName, registrationId);

		// 제공자가 발급한 고유 식별자 => username
		String userIdentify = oAuthAttributes.getUserIdentify();

		// 사용자의 프로필 이름
		String name = oAuthAttributes.getName();

		// OAuth 인증 정보가 담긴 DTO 생성
		OAuthDTO oAuthDTO = new OAuthDTO(userIdentify, name, oAuthAttributes.getOAuthProvider());

		// 최초 로그인 또는 로그인 시 DB의 PK인 userId를 반환
		String userId = String.valueOf(saveOrUpdate(oAuthDTO));

		// OAuth2User 에 userId 추가
		oAuthAttributes.getAttributes().put("userId", userId);

		// 일반 사용자 권한 설정
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(USER);

		// OAuth2User 객체 생성 및 반환
		return new DefaultOAuth2User(
			Collections.singleton(grantedAuthority),
			oAuthAttributes.getAttributes(),
			"userId");
	}

	/**
	 * 기존 가입자인지 확인 <br> 최초 로그인 시 가입 절차 진행
	 */
	private int saveOrUpdate(OAuthDTO oAuthDTO) {
		Optional<OAuthVO> oAuthVO = authMapper.selectOAuthByUsername(oAuthDTO.getUsername());
		return oAuthVO.map(OAuthVO::getUserId)
			.orElseGet(() -> signupOAuth(oAuthDTO));
	}

	/**
	 * 가입과 동시에 반환된 oAuthVO 객체로부터 userId 반환
	 */
	private int signupOAuth(OAuthDTO oAuthDTO) {
		OAuthVO oAuthVO = OAuthVO.of(oAuthDTO);
		authMapper.insertOAuth(oAuthVO);
		return oAuthVO.getUserId();
	}

	/**
	 * 제공자에 따라 다르게 생성자 호출
	 */
	private OAuthAttributes getAttributeByProvider(Map<String, Object> attributes, String nameAttributeKey, String registrationId) {
		return switch (registrationId.toLowerCase()) {
			case "kakao" -> new KakaoAttributes(attributes, nameAttributeKey);
			case "google" -> new GoogleAttributes(attributes, nameAttributeKey);
			case "naver" -> new NaverAttributes((Map)attributes.get("response"), nameAttributeKey);
			default -> throw new CustomAuthenticationException("알 수 없는 제공자입니다.: " + registrationId);
		};
	}
}
