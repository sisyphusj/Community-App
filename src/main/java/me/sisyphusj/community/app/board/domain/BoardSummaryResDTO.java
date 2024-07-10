package me.sisyphusj.community.app.board.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardSummaryResDTO {

	// 게시글 고유 ID
	private String boardId;

	// 작성자
	private String author;

	// 제목
	private String title;

	// 생성 시간
	private LocalDateTime createdDate;

	public static BoardSummaryResDTO of(BoardSummaryVO boardVO) {
		return BoardSummaryResDTO.builder()
			.boardId(boardVO.getBoardId())
			.author(boardVO.getAuthor())
			.title(boardVO.getTitle())
			.createdDate(boardVO.getCreatedDate())
			.build();
	}
}
