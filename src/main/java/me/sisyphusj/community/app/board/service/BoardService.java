package me.sisyphusj.community.app.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.board.domain.BoardReqDTO;
import me.sisyphusj.community.app.board.domain.BoardSummaryResDTO;
import me.sisyphusj.community.app.board.domain.BoardVO;
import me.sisyphusj.community.app.board.mapper.BoardMapper;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper boardMapper;

	public void createdBoard(BoardReqDTO boardReqDTO) {
		boardMapper.insertBoard(BoardVO.of(boardReqDTO));
	}

	public List<BoardSummaryResDTO> getBoardList() {
		return boardMapper.selectBoardSummaryList().stream()
			.map(BoardSummaryResDTO::of)
			.toList();
	}
}
