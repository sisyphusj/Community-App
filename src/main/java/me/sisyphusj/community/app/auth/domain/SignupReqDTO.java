package me.sisyphusj.community.app.auth.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupReqDTO {

	// 사용자 로그인 아이디
	@NotBlank
	@Size(max = 20, message = "아이디는 최대 20자까지 입력 가능합니다.")
	private String username;

	// 사용자 이름
	@NotBlank
	@Size(max = 30, message = "사용자 이름은 최대 30자까지 입력 가능합니다.")
	private String name;

	// 비밀번호
	@NotBlank
	@Size(max = 15, message = "비밀번호는 최대 15자까지 입력 가능합니다.")
	@Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).+$", message = "비밀번호에는 최소 하나의 특수 문자가 포함되어야 합니다.")
	private String password;

	public void updatePassword(String password) {
		this.password = password;
	}
}
