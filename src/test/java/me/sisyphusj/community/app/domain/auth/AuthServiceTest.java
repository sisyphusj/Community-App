package me.sisyphusj.community.app.domain.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import me.sisyphusj.community.app.auth.domain.SignupReqDTO;
import me.sisyphusj.community.app.auth.domain.SignupVO;
import me.sisyphusj.community.app.auth.mapper.AuthMapper;
import me.sisyphusj.community.app.auth.service.AuthService;
import me.sisyphusj.community.app.commons.exception.AlertException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private AuthMapper authMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthService authService;

	@Test
	@DisplayName("회원정보 저장 성공 - DTO 유효, 중복 없음")
	void 회원정보_저장_성공() {
		// given
		SignupReqDTO signupReqDTO = signupDTO();

		given(authMapper.selectCountByUsername(anyString())).willReturn(0);
		given(authMapper.selectCountByName(anyString())).willReturn(0);
		given(passwordEncoder.encode(anyString())).willReturn("password");

		// when
		authService.signup(signupReqDTO);

		// then
		then(authMapper).should(times(1)).insertAuth(any(SignupVO.class));
	}

	@Test
	@DisplayName("회원가입 실패 - 아이디 중복")
	void 회원가입_실패_아이디_중복() {
		// given
		SignupReqDTO signupReqDTO = signupDTO();

		// username 중복검사에서 중복 존재
		given(authMapper.selectCountByUsername(anyString())).willReturn(1);

		// when, then
		assertThatThrownBy(() -> authService.signup(signupReqDTO))
			.isInstanceOf(AlertException.class)
			.hasMessageContaining("아이디 중복");

		then(authMapper).should(never()).insertAuth(any(SignupVO.class));
	}

	@Test
	@DisplayName("회원가입 실패 - 이름 중복")
	void 회원가입_실패_이름_중복() {
		// given
		SignupReqDTO signupReqDTO = signupDTO();

		// name 중복 검사에서 중복 존재
		given(authMapper.selectCountByUsername(anyString())).willReturn(0);
		given(authMapper.selectCountByName(anyString())).willReturn(1);

		// when, then
		assertThatThrownBy(() -> authService.signup(signupReqDTO))
			.isInstanceOf(AlertException.class)
			.hasMessageContaining("사용자 이름 중복");

		then(authMapper).should(never()).insertAuth(any(SignupVO.class));
	}

	private SignupReqDTO signupDTO() {
		return SignupReqDTO.builder()
			.username("testUser")
			.password("testPass")
			.name("duplicateName")
			.build();
	}
}
