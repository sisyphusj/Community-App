package me.sisyphusj.community.app.board.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardReqDTO {

	private String title;

	private String content;

}
