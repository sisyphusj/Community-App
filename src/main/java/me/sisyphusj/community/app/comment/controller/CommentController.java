package me.sisyphusj.community.app.comment.controller;

import static me.sisyphusj.community.app.commons.Constants.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.domain.CommentEditReqDTO;
import me.sisyphusj.community.app.comment.domain.CommentReqDTO;
import me.sisyphusj.community.app.comment.service.CommentService;
import me.sisyphusj.community.app.commons.LocationUrl;

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

		model.addAttribute(MESSAGE, "댓글이 등록되었습니다.");
		model.addAttribute(LOCATION_URL, LocationUrl.CUSTOM);
		model.addAttribute(CUSTOM_URL, BASE_POST_URL + commentReqDTO.getPostId());
		return MAV_ALERT;
	}

	/**
	 * 댓글 수정
	 */
	@PostMapping("/edit")
	public String editComment(@Valid @ModelAttribute CommentEditReqDTO commentEditReqDTO, Model model) {
		commentService.editComment(commentEditReqDTO);

		model.addAttribute(MESSAGE, "댓글이 수정되었습니다.");
		model.addAttribute(LOCATION_URL, LocationUrl.CUSTOM);
		model.addAttribute(CUSTOM_URL, BASE_POST_URL + commentEditReqDTO.getPostId());
		return MAV_ALERT;
	}

	/**
	 * 댓글 삭제
	 */
	@GetMapping("/{postId}/remove")
	public String removeComment(@PathVariable long postId, @RequestParam long commentId, Model model) {
		commentService.removeComment(commentId);

		model.addAttribute(MESSAGE, "댓글이 삭제되었습니다.");
		model.addAttribute(LOCATION_URL, LocationUrl.CUSTOM);
		model.addAttribute(CUSTOM_URL, BASE_POST_URL + postId);
		return MAV_ALERT;
	}
}
