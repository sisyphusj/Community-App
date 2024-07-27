package me.sisyphusj.community.app.post_like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.post_like.service.PostLikeService;

@Controller
@RequestMapping("/post/likes")
@RequiredArgsConstructor
public class PostLikeController {

	private final PostLikeService postLikeService;

	/**
	 * 게시글에 좋아요 추가
	 */
	@PostMapping
	public ResponseEntity<Integer> addLikePost(@RequestParam long postId) {
		return ResponseEntity.ok(postLikeService.addLikePost(postId));
	}

	/**
	 * 게시글 좋아요 해제
	 */
	@DeleteMapping("/dislike")
	public ResponseEntity<Integer> disLikePost(@RequestParam long postId) {
		return ResponseEntity.ok(postLikeService.disLikePost(postId));
	}
}
