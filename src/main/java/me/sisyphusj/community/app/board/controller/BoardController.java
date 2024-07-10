package me.sisyphusj.community.app.board.controller;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.board.domain.BoardThumbnailResDTO;
import me.sisyphusj.community.app.board.domain.CreateBoardReqDTO;
import me.sisyphusj.community.app.board.service.BoardService;
import me.sisyphusj.community.app.commons.exception.RedirectType;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	/**
	 * 게시글 리스트 페이지
	 */
	@GetMapping
	public String showBoardPage() {
		return "board";
	}

	/**
	 * 게시글 섬네일 리스트 불러오기
	 */
	@GetMapping("/posts")
	public ResponseEntity<List<BoardThumbnailResDTO>> getBoardThumbnailList(@RequestParam int amount, @RequestParam int offset) {
		return ResponseEntity.ok(boardService.getBoardThumbnailList(amount, offset));
	}

	/**
	 * 게시글 페이지
	 */
	@GetMapping("/new")
	public String showPostPage() {
		return "newPost";
	}

	/**
	 * 게시글 추가
	 */
	@PostMapping("/add")
	public String createBoard(@Valid @ModelAttribute CreateBoardReqDTO createBoardReqDTO, Model model) {
		boardService.createdBoard(createBoardReqDTO);

		model.addAttribute(MESSAGE, "저장되었습니다.");
		model.addAttribute(REDIRECT_URL, RedirectType.HOME);
		return MAV_ALERT;
	}
}
