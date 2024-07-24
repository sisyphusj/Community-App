package me.sisyphusj.community.app.image.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentImageResDTO {

	private long imageId; // 이미지 고유 ID

	private long postId; // 게시글 고유 ID

	private String originName; // 파일 원본 이름

	private String storedName; // 저장소에 저장된 파일의 이름

	private long size; // 이미지 크기

	private String imagePath; // 저장소의 Image 주소

	private Date createdAt; // 이미지 업로드 날짜

	public static CommentImageResDTO of(ImageVO imageVO) {
		return CommentImageResDTO.builder()
			.imageId(imageVO.getImageId())
			.postId(imageVO.getPostId())
			.originName(imageVO.getOriginName())
			.storedName(imageVO.getStoredName())
			.imagePath(imageVO.getImagePath())
			.createdAt(imageVO.getCreatedAt())
			.build();
	}
}
