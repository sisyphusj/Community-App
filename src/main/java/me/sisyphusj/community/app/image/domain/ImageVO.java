package me.sisyphusj.community.app.image.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageVO {

	private Long imageId; // 이미지 고유 ID

	private Long userId; // 작성자 고유 ID

	private Long postId; // 게시글 고유 ID

	private Long commentId; // 댓글 고유 ID

	private String originalName; // 파일 원본 이름

	private String storedName; // 저장소에 저장된 파일의 이름

	private long size; // 이미지 크기

	private String imagePath; // 저장소의 Image 주소

	private Date createdDate; // 이미지 업로드 날짜

	public ImageVO updatePostId(long postId) {
		this.postId = postId;
		return this;
	}

	public ImageVO updateCommentId(long commentId) {
		this.commentId = commentId;
		return this;
	}

	public static ImageVO of(ImageMetadata imageMetadata) {
		return ImageVO.builder()
			.userId(SecurityUtil.getLoginUserId())
			.originalName(imageMetadata.getOriginalName())
			.storedName(imageMetadata.getStoredName())
			.size(imageMetadata.getSize())
			.imagePath(imageMetadata.getImagePath())
			.build();
	}
}
