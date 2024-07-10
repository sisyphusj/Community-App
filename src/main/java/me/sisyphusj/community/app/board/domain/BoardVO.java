package me.sisyphusj.community.app.board.domain;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SessionUtil;

@Builder
@Getter
public class BoardVO {

	private int userId;

	private String title;

	private String content;

	public static BoardVO of(CreateBoardReqDTO createBoardReqDTO) {
		return BoardVO.builder()
			.userId(SessionUtil.getLoginUserId())
			.title(createBoardReqDTO.getTitle())
			.content(createBoardReqDTO.getContent())
			.build();
	}
}
