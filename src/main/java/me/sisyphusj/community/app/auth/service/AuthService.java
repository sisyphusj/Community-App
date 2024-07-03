package me.sisyphusj.community.app.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.auth.domain.SignupReqDTO;
import me.sisyphusj.community.app.auth.domain.SignupVO;
import me.sisyphusj.community.app.auth.mapper.AuthMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthMapper authMapper;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void signup(SignupReqDTO signupReqDTO) {

		if (authMapper.selectCountByUsername(signupReqDTO.getUsername()) > 0) {
			throw new IllegalStateException("아이디 중복");
		}

		if (authMapper.selectCountByName(signupReqDTO.getName()) > 0) {
			throw new IllegalStateException("사용자 이름 중복");
		}

		String newPassword = passwordEncoder.encode(signupReqDTO.getPassword());

		signupReqDTO.updatePassword(newPassword);

		SignupVO signupVO = SignupVO.of(signupReqDTO);

		authMapper.insertAuth(signupVO);
	}
}
