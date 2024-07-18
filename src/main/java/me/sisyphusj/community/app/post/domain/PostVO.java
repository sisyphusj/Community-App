package me.sisyphusj.community.app.post.domain;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Builder
@Getter
public class PostVO {

	private Long postId; // 게시글 고유 ID

	private Long userId; // 사용자 고유 ID

	private String title; // 제목

	private String content; // 본문

	private HasImage hasImage; // 게시글의 이미지 첨부 여부

	public static PostVO of(PostCreateReqDTO postCreateReqDTO) {
		return PostVO.builder()
			.userId(SecurityUtil.getLoginUserId())
			.title(postCreateReqDTO.getTitle())
			.content(postCreateReqDTO.getContent())
			.hasImage(postCreateReqDTO.getHasImage())
			.build();
	}

	public static PostVO of(PostEditReqDTO postEditReqDTO) {
		return PostVO.builder()
			.postId(postEditReqDTO.getPostId())
			.userId(SecurityUtil.getLoginUserId())
			.title(postEditReqDTO.getTitle())
			.content(postEditReqDTO.getContent())
			.hasImage(postEditReqDTO.getHasImage())
			.build();

	}
}
