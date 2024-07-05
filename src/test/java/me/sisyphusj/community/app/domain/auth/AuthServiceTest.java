package me.sisyphusj.community.app.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import me.sisyphusj.community.app.auth.domain.SignupReqDTO;
import me.sisyphusj.community.app.auth.domain.SignupVO;
import me.sisyphusj.community.app.auth.mapper.AuthMapper;
import me.sisyphusj.community.app.commons.exception.AlertException;

class AuthServiceTest {

	@Mock
	private AuthMapper authMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthService authService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("회원정보 저장 성공 - DTO 유효, 중복 없음")
	void 회원정보_저장_성공() {
		// given
		SignupReqDTO signupReqDTO = signupDTO();

		when(authMapper.selectCountByUsername(anyString())).thenReturn(0);
		when(authMapper.selectCountByName(anyString())).thenReturn(0);
		when(passwordEncoder.encode(anyString())).thenReturn("password");

		// when
		authService.signup(signupReqDTO);

		// then
		verify(authMapper, times(1)).insertAuth(any(SignupVO.class));
	}

	@Test
	@DisplayName("회원가입 실패 - 아이디 중복")
	void 회원가입_실패_아이디_중복() {
		// given
		SignupReqDTO signupReqDTO = signupDTO();

		// username 중복검사에서 중복 존재
		when(authMapper.selectCountByUsername(anyString())).thenReturn(1);

		// when, then
		AlertException exception = assertThrows(AlertException.class, () -> authService.signup(signupReqDTO));
		assertEquals("아이디 중복", exception.getMessage());
		verify(authMapper, never()).insertAuth(any(SignupVO.class));
	}

	@Test
	@DisplayName("회원가입 실패 - 이름 중복")
	void 회원가입_실패_이름_중복() {
		// given
		SignupReqDTO signupReqDTO = signupDTO();

		// name 중복 검사에서 중복 존재
		when(authMapper.selectCountByUsername(anyString())).thenReturn(0);
		when(authMapper.selectCountByName(anyString())).thenReturn(1);

		// when, then
		AlertException exception = assertThrows(AlertException.class, () -> authService.signup(signupReqDTO));
		assertEquals("사용자 이름 중복", exception.getMessage());
		verify(authMapper, never()).insertAuth(any(SignupVO.class));
	}

	private SignupReqDTO signupDTO() {
		return SignupReqDTO.builder()
			.username("testUser")
			.password("testPass")
			.name("duplicateName")
			.build();
	}
}
