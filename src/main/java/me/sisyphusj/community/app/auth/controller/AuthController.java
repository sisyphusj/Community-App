package me.sisyphusj.community.app.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.auth.domain.SignupReqDTO;
import me.sisyphusj.community.app.auth.service.AuthService;
import me.sisyphusj.community.app.commons.exception.BlankInputException;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * 회원가입 페이지 연결
	 */
	@GetMapping("/signup")
	public String showSignupPage() {
		return "signup";
	}

	/**
	 * 회원가입
	 */
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute SignupReqDTO signupReqDTO) {
		authService.signup(signupReqDTO);
		return "home";
	}

	/**
	 * 사용자 아이디 중복 검사 <br> 중복이면 TRUE
	 * java.lang.Boolean 처리를 위해 	@ResponseBody 추가
	 */
	@GetMapping("/check/username")
	public ResponseEntity<Boolean> isUsernameDuplicated(@RequestParam String username) {
		if (username.trim().isEmpty()) {
			throw new BlankInputException();
		}

		return ResponseEntity.ok(authService.isUsernameDuplicated(username));
	}

	/**
	 * 로그인 페이지 연결
	 */
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	/**
	 * 세션 인증이 작동하는지 확인하는 페이지
	 */
	@GetMapping("/my-page")
	public String showMyPage(Model model) {
		model.addAttribute("user", SecurityUtil.getLoginUserId());
		return "myPage";
	}
}
