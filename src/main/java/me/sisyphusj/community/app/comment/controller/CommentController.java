package me.sisyphusj.community.app.comment.controller;

import static me.sisyphusj.community.app.commons.Constants.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.domain.CommentEditReqDTO;
import me.sisyphusj.community.app.comment.domain.CommentReqDTO;
import me.sisyphusj.community.app.comment.service.CommentService;
import me.sisyphusj.community.app.commons.LocationUrl;
import me.sisyphusj.community.app.post.domain.BoardType;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	/**
	 * 댓글 등록
	 */
	@PostMapping
	public String createComment(@Valid @ModelAttribute CommentReqDTO commentReqDTO, Model model) {
		commentService.createComment(commentReqDTO);
		return setCommonAttributes(commentReqDTO.getPostId(), "댓글이 등록되었습니다.", commentReqDTO.getBoardType(), model);
	}

	/**
	 * 댓글 수정
	 */
	@PostMapping("/edit")
	public String editComment(@Valid @ModelAttribute CommentEditReqDTO commentEditReqDTO, Model model) {
		commentService.editComment(commentEditReqDTO);
		return setCommonAttributes(commentEditReqDTO.getPostId(), "댓글이 수정되었습니다.", commentEditReqDTO.getBoardType(), model);
	}

	/**
	 * 댓글 삭제
	 */
	@GetMapping("{boardType}/{postId}/remove/{commentId}")
	public String removeComment(@PathVariable BoardType boardType, @PathVariable long postId, @PathVariable long commentId, Model model) {
		commentService.removeComment(postId, commentId);
		return setCommonAttributes(postId, "댓글이 삭제되었습니다.", boardType, model);
	}

	/**
	 * 댓글 기능 수행 후 사용자 알림을 위한 공통 속성을 설정
	 *
	 * @param postId 게시글 고유 ID
	 * @param message 사용자 알림 메시지
	 * @param boardType 게시판 타입
	 * @param model model
	 * @return 사용자 알림 JSP
	 */
	private String setCommonAttributes(long postId, String message, BoardType boardType, Model model) {
		model.addAttribute(MESSAGE, message);
		model.addAttribute(LOCATION_URL, LocationUrl.CUSTOM);
		model.addAttribute(CUSTOM_URL, "/community/" + boardType + "/posts/" + postId);
		return MAV_ALERT;
	}
}
