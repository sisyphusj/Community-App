package me.sisyphusj.community.app.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "home";
	}
}
