package me.sisyphusj.community.app.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.board.domain.BoardThumbnailResDTO;
import me.sisyphusj.community.app.board.domain.BoardVO;
import me.sisyphusj.community.app.board.domain.CreateBoardReqDTO;
import me.sisyphusj.community.app.board.mapper.BoardMapper;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper boardMapper;

	@Transactional
	public void createdBoard(CreateBoardReqDTO createBoardReqDTO) {
		boardMapper.insertBoard(BoardVO.of(createBoardReqDTO));
	}

	@Transactional(readOnly = true)
	public List<BoardThumbnailResDTO> getBoardThumbnailList(int amount, int offset) {
		return boardMapper.selectBoardThumbnailList().stream()
			.map(BoardThumbnailResDTO::of)
			.toList();
	}
}
