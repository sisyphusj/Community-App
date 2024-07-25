package me.sisyphusj.community.app.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.like.service.LikeService;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	/**
	 * 게시글에 좋아요 추가
	 */
	@GetMapping("/post")
	public ResponseEntity<Integer> addLikePost(@RequestParam long postId) {
		return ResponseEntity.ok(likeService.addLikePost(postId));
	}

	/**
	 * 댓글에 좋아요 추가
	 */
	@GetMapping("/comment")
	public ResponseEntity<Integer> addLikeComment(@RequestParam long commentId) {
		return ResponseEntity.ok(likeService.addLikeComment(commentId));
	}

	/**
	 * 사용자가 게시글에 좋아요를 남겼는지 확인
	 *
	 * @return 사용자가 게시글에 좋아요를 남겼으면 true, 남기지 않았으면 false 반환
	 */
	@GetMapping("/post/check")
	public ResponseEntity<Boolean> hasPostLike(@RequestParam long postId) {
		return ResponseEntity.ok(likeService.hasPostLike(postId));
	}

	/**
	 * 사용자가 댓글에 좋아요를 남겼는지 확인
	 *
	 * @return 사용자가 댓글에 좋아요를 남겼으면 true, 남기지 않았으면 false 반환
	 */
	@GetMapping("/comment/check")
	public ResponseEntity<Boolean> hasCommentLike(@RequestParam long commentId) {
		return ResponseEntity.ok(likeService.hasCommentLike(commentId));
	}

	/**
	 * 게시글 좋아요 해제
	 */
	@GetMapping("/post/dislike")
	public ResponseEntity<Integer> disLikePost(@RequestParam long postId) {
		return ResponseEntity.ok(likeService.disLikePost(postId));
	}

	/**
	 * 댓글 좋아요 해제
	 */
	@GetMapping("/comment/dislike")
	public ResponseEntity<Integer> disLikeComment(@RequestParam long commentId) {
		return ResponseEntity.ok(likeService.disLikeComment(commentId));
	}
}
