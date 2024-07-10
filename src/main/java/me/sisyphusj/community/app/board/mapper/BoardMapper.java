package me.sisyphusj.community.app.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.board.domain.BoardThumbnailVO;
import me.sisyphusj.community.app.board.domain.BoardVO;

@Mapper
public interface BoardMapper {

	void insertBoard(BoardVO boardVO);

	List<BoardThumbnailVO> selectBoardThumbnailList();

}
