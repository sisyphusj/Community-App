package me.sisyphusj.community.app.image.domain;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Builder
@Getter
public class ImageInsertVO {

	private int userId; // 작성자 고유 ID

	private int postId; // 게시글 고유 ID

	private String originalName; // 파일 원본 이름

	private String storedName; // 저장소에 저장된 파일의 이름

	private long size; // 이미지 크기

	private String imagePath; // 저장소의 Image 주소

	public ImageInsertVO updatePostId(int postId) {
		this.postId = postId;
		return this;
	}

	public static ImageInsertVO of(ImageDetailReqDTO imageDetailReqDTO) {
		return ImageInsertVO.builder()
			.userId(Integer.parseInt(SecurityUtil.getLoginUserId()))
			.originalName(imageDetailReqDTO.getOriginalName())
			.storedName(imageDetailReqDTO.getStoredName())
			.size(imageDetailReqDTO.getSize())
			.imagePath(imageDetailReqDTO.getImagePath())
			.build();
	}
}
