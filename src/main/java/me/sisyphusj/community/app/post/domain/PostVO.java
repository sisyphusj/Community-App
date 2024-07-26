package me.sisyphusj.community.app.post.domain;

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
public class PostVO {

	private Long postId; // 게시글 고유 ID

	private Long userId; // 사용자 고유 ID

	private String name; // 작성자 이름

	private String title; // 제목

	private String content; // 본문

	private int views; // 조회 수

	private Date createdAt; // 생성일

	private Date updatedAt; // 최종 수정일

	private int likes; // 게시글 좋아요 수

	public static PostVO of(PostCreateReqDTO postCreateReqDTO) {
		return PostVO.builder()
			.userId(SecurityUtil.getLoginUserId())
			.title(postCreateReqDTO.getTitle())
			.content(postCreateReqDTO.getContent())
			.build();
	}

	public static PostVO of(PostEditReqDTO postEditReqDTO) {
		return PostVO.builder()
			.postId(postEditReqDTO.getPostId())
			.userId(SecurityUtil.getLoginUserId())
			.title(postEditReqDTO.getTitle())
			.content(postEditReqDTO.getContent())
			.build();
	}
}
