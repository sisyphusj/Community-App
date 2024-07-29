package me.sisyphusj.community.app.post.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateReqDTO {

	@NotBlank
	@Size(max = 50, message = "제목은 최대 50자까지 쓸 수 있습니다.")
	private String title; // 제목

	@NotBlank
	@Size(max = 500, message = "본문은 최대 500자까지 쓸 수 있습니다.")
	private String content; // 본문

	private List<MultipartFile> images; // 추가된 이미지 리스트

	private MultipartFile thumbnail; // 썸네일 이미지

	@NotNull
	private BoardType boardType; // 게시판 타입

}
