package me.sisyphusj.community.app.image.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ImageDetailReqDTO {
	
	@NotBlank
	private String originalName; // 파일 원본 이름

	@NotBlank
	private String storedName; // 저장소에 저장된 파일의 이름

	@NotNull
	private long size; // 이미지 크기

	@NotBlank
	private String imagePath; // 저장소의 Image 주소

}
