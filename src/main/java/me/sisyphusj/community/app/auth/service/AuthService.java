package me.sisyphusj.community.app.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.auth.domain.SignupReqDTO;
import me.sisyphusj.community.app.auth.domain.SignupVO;
import me.sisyphusj.community.app.auth.mapper.AuthMapper;
import me.sisyphusj.community.app.commons.LocationUrl;
import me.sisyphusj.community.app.commons.exception.AlertException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthMapper authMapper;

	private final PasswordEncoder passwordEncoder;

	/**
	 * 사용자 회원가입
	 */
	@Transactional
	public void signup(SignupReqDTO signupReqDTO) {

		// 사용자 아이디 중복 체크
		if (authMapper.selectCountByUsername(signupReqDTO.getUsername()) > 0) {
			throw AlertException.of400("아이디 중복", LocationUrl.BACK);
		}

		String newPassword = passwordEncoder.encode(signupReqDTO.getPassword());

		signupReqDTO.updatePassword(newPassword);

		SignupVO signupVO = SignupVO.of(signupReqDTO);

		// 사용자 정보 삽입
		authMapper.insertAuth(signupVO);
	}

	/**
	 * 사용자 아이디 중복 체크
	 */
	@Transactional
	public boolean isUsernameDuplicated(String username) {
		return authMapper.selectCountByUsername(username) > 0;
	}
}
