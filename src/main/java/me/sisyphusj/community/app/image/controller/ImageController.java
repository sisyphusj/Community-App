package me.sisyphusj.community.app.image.controller;

import java.util.List;

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

	@GetMapping
	public ResponseEntity<List<ImageDetailsResDTO>> getImages(@RequestParam int postId) {
		return ResponseEntity.ok(imageService.getImages(postId));
	}
}
