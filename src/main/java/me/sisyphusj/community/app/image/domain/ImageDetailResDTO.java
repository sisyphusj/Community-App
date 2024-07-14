package me.sisyphusj.community.app.image.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDetailResDTO {

	private int imageId; // 이미지 고유 ID

	private int postId; // 게시글 고유 ID

	private String originalName; // 파일 원본 이름

	private String storedName; // 저장소에 저장된 파일의 이름

	private long size; // 이미지 크기

	private String imagePath; // 저장소의 Image 주소

	private Date createdDate; // 이미지 업로드 날짜

	public static ImageDetailResDTO of(ImageSelectVO imageSelectVO) {
		return ImageDetailResDTO.builder()
			.imageId(imageSelectVO.getImageId())
			.postId(imageSelectVO.getPostId())
			.originalName(imageSelectVO.getOriginalName())
			.storedName(imageSelectVO.getStoredName())
			.imagePath(imageSelectVO.getImagePath())
			.createdDate(imageSelectVO.getCreatedDate())
			.build();
	}
}
