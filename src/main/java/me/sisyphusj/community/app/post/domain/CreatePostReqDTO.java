package me.sisyphusj.community.app.post.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePostReqDTO {

	@NotBlank
	@Max(value = 60, message = "제목은 최대 60자까지 쓸 수 있습니다.")
	private String title;

	@NotBlank
	@Max(value = 500, message = "본문은 최대 500자까지 쓸 수 있습니다.")
	private String content;

}
