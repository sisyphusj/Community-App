package me.sisyphusj.community.app.board.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBoardReqDTO {

	@NotBlank
	private String title;

	@NotBlank
	private String content;

}
