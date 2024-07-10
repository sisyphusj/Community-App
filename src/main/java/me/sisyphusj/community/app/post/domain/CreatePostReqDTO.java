package me.sisyphusj.community.app.post.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePostReqDTO {

	@NotBlank
	private String title;

	@NotBlank
	private String content;

}
