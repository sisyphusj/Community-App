package me.sisyphusj.community.app.domain.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.auth.controller.AuthController;
import me.sisyphusj.community.app.auth.domain.SignupReqDTO;
import me.sisyphusj.community.app.auth.service.AuthService;
import me.sisyphusj.community.app.commons.exception.AlertException;
import me.sisyphusj.community.app.commons.exception.GlobalExceptionAdvice;
import me.sisyphusj.community.app.commons.exception.RedirectType;

@Slf4j
@WebMvcTest(AuthController.class)
class AuthControllerTest {

	@MockBean
	private AuthService authService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(authService))
			.setControllerAdvice(new GlobalExceptionAdvice())
			.setViewResolvers(viewResolver())
			.build();
	}

	@Test
	@DisplayName("회원가입 성공")
	void 회원가입_성공() throws Exception {
		// given
		SignupReqDTO signupReqDTO = createSignupReqDTO();
		willDoNothing().given(authService).signup(any(SignupReqDTO.class));

		// when
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", signupReqDTO.getUsername())
				.param("password", signupReqDTO.getPassword())
				.param("name", signupReqDTO.getName())
		);

		// then
		actions.andExpect(redirectedUrl("/"));
		then(authService).should().signup(any(SignupReqDTO.class));
		assertThat(actions.andReturn().getResponse().getStatus()).isEqualTo(302);
	}

	@Test
	@DisplayName("회원가입 실패 - 아이디 중복 예외")
	void 회원가입_실패_아이디_중복() throws Exception {
		// given
		SignupReqDTO signupReqDTO = createSignupReqDTO();
		willThrow(AlertException.of400("아이디 중복", RedirectType.BACK)).given(authService).signup(any(SignupReqDTO.class));

		// when
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", signupReqDTO.getUsername())
				.param("password", signupReqDTO.getPassword())
				.param("name", signupReqDTO.getName())
		);

		// then
		actions.andExpect(view().name("error/alert"));
		actions.andExpect(model().attributeExists("message"));
		actions.andExpect(model().attribute("message", "아이디 중복"));
		then(authService).should().signup(any(SignupReqDTO.class));
	}

	@Test
	@DisplayName("회원가입 실패 - 이름 중복 예외")
	void 회원가입_실패_이름_중복() throws Exception {
		// given
		SignupReqDTO signupReqDTO = createSignupReqDTO();
		willThrow(AlertException.of400("사용자 이름 중복", RedirectType.BACK)).given(authService).signup(any(SignupReqDTO.class));

		// when
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", signupReqDTO.getUsername())
				.param("password", signupReqDTO.getPassword())
				.param("name", signupReqDTO.getName())
		);

		// then
		actions.andExpect(view().name("error/alert"));
		actions.andExpect(model().attributeExists("message"));
		actions.andExpect(model().attribute("message", "사용자 이름 중복"));
		then(authService).should().signup(any(SignupReqDTO.class));
	}

	private SignupReqDTO createSignupReqDTO() {
		return SignupReqDTO.builder()
			.username("test")
			.password("test")
			.name("test")
			.build();
	}

	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
