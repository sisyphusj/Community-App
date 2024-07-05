package me.sisyphusj.community.app.domain.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import me.sisyphusj.community.app.auth.service.AuthService;

@Slf4j
@WebMvcTest(AuthController.class)
class AuthControllerTest {

	@Mock
	private AuthService authService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(
				new AuthController(authService)
			).setViewResolvers(viewResolver())
			.build();
	}

	@Test
	@DisplayName("회원가입 성공 모든 필드가 있는 경우")
	void 회원가입_성공() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", "testUser")
				.param("password", "testPass")
				.param("name", "test")
		);

		// then
		resultActions.andExpect(redirectedUrl("/"));
	}

	@Test
	@DisplayName("회원가입 실패 - 한개의 필드가 빠진 경우")
	void 회원가입_실패_필드_1개_없음() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", "")
				.param("password", "testPass")
				.param("name", "test")
		);

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("회원가입 실패 - 모든 필드가 없는 경우")
	void 회원가입_실패_모든_필드_없음() throws Exception {

		// when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("username", "")
				.param("password", "")
				.param("name", "")
		);

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	private ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver =
			new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
