package me.sisyphusj.community.app.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.auth.SessionConst;
import me.sisyphusj.community.app.user.domain.UserSignupReqDTO;
import me.sisyphusj.community.app.user.service.UserService;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/signup")
	public String showSignupPage() {
		return "signup";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute UserSignupReqDTO userSignupReqDTO) {
		userService.signup(userSignupReqDTO);
		log.info("register complete");
		return "redirect:/";
	}

	/**
	 * 세션 인증이 작동하는지 확인하는 페이지
	 */
	@GetMapping("/mypage")
	public String showMyPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		model.addAttribute("user", session.getAttribute(SessionConst.LOGIN_USER));

		return "mypage";
	}

}
