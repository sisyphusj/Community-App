package me.sisyphusj.community.app.comment_like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment_like.service.CommentLikeService;

@Controller
@RequestMapping("/comment/likes")
@RequiredArgsConstructor
public class CommentLikeController {

	private final CommentLikeService commentLikeService;

	/**
	 * 댓글에 좋아요 추가
	 */
	@PostMapping
	public ResponseEntity<Integer> addLikeComment(@RequestParam long commentId) {
		return ResponseEntity.ok(commentLikeService.addLikeComment(commentId));
	}

	/**
	 * 댓글 좋아요 해제
	 */
	@DeleteMapping("/dislike")
	public ResponseEntity<Integer> disLikeComment(@RequestParam long commentId) {
		return ResponseEntity.ok(commentLikeService.disLikeComment(commentId));
	}
}
