package me.sisyphusj.community.app.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.user.domain.UserSignupReqDTO;
import me.sisyphusj.community.app.user.domain.UserVO;
import me.sisyphusj.community.app.user.mapper.UserMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;

	private final PasswordEncoder passwordEncoder;

	public void signup(UserSignupReqDTO userSignupReqDTO) {

		if (userMapper.selectCountByUsername(userSignupReqDTO.getUsername()) > 0) {
			throw new IllegalStateException("아이디 중복");
		}

		if (userMapper.selectCountByName(userSignupReqDTO.getName()) > 0) {
			throw new IllegalStateException("사용자 이름 중복");
		}

		String newPassword = passwordEncoder.encode(userSignupReqDTO.getPassword());

		userSignupReqDTO.updatePassword(newPassword);

		UserVO userVO = UserVO.of(userSignupReqDTO);

		userMapper.insertUser(userVO);
	}
}
