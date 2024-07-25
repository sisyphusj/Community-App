package me.sisyphusj.community.app.image.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.image.service.ImageService;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	/**
	 * 게시글 이미지 삭제
	 */
	@GetMapping("post/remove")
	public ResponseEntity<Void> removePostImage(@RequestParam long postId, @RequestParam long imageId) {
		imageService.removePostImage(postId, imageId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 댓글 이미지 삭제
	 */
	@GetMapping("comment/remove")
	public ResponseEntity<Void> removeCommentImage(@RequestParam long commentId, @RequestParam long imageId) {
		imageService.removeCommentImage(commentId, imageId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
