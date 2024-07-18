package me.sisyphusj.community.app.image.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.image.domain.ImageDetailsResDTO;
import me.sisyphusj.community.app.image.service.ImageService;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	/**
	 * 게시글에 등록된 이미지 리스트 조회
	 */
	@GetMapping
	public ResponseEntity<List<ImageDetailsResDTO>> getImages(@RequestParam long postId) {
		return ResponseEntity.ok(imageService.getImages(postId));
	}

	/**
	 * 이미지 삭제
	 */
	@GetMapping("/remove")
	public ResponseEntity<Void> removeImage(@RequestParam long imageId) {
		imageService.removeImage(imageId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
