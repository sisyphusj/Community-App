package me.sisyphusj.community.app.image.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageMetadata {

	// 파일 원본 이름
	private String originalName;

	// 저장소에 저장된 파일의 이름
	private String storedName;

	// 이미지 크기
	private long size;

	// 저장소의 Image 주소
	private String imagePath;

}
