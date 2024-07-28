package me.sisyphusj.community.app.image.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ThumbnailImageResDTO {

	private long imageId; // 이미지 고유 ID

	private String originName; // 파일 원본 이름

	private String storedName; // 저장소에 저장된 파일의 이름

	private long size; // 이미지 크기

	private String imagePath; // 저장소의 Image 주소

	public static ThumbnailImageResDTO of(ImageVO imageVO) {
		return ThumbnailImageResDTO.builder()
			.imageId(imageVO.getImageId())
			.originName(imageVO.getOriginName())
			.storedName(imageVO.getStoredName())
			.size(imageVO.getSize())
			.imagePath(imageVO.getImagePath())
			.build();
	}
}
