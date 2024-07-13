package me.sisyphusj.community.app.image.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.image.domain.ImageUploadReqDTO;
import me.sisyphusj.community.app.image.domain.ImageUploadResDTO;
import me.sisyphusj.community.app.image.service.ImageService;

@Slf4j
@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	@PostMapping("/upload")
	public ResponseEntity<List<ImageUploadResDTO>> uploadImage(@Valid @ModelAttribute ImageUploadReqDTO imageUploadReqDTO) {
		return ResponseEntity.ok(imageService.uploadImages(imageUploadReqDTO));
	}
}
