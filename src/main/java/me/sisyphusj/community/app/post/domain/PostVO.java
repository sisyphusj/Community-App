package me.sisyphusj.community.app.post.domain;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Builder
@Getter
public class PostVO {

	private long postId;

	private long userId;

	private String title;

	private String content;

	private HasImage hasImage;

	public static PostVO of(CreatePostReqDTO createPostReqDTO) {
		return PostVO.builder()
			.userId(SecurityUtil.getLoginUserId())
			.title(createPostReqDTO.getTitle())
			.content(createPostReqDTO.getContent())
			.hasImage(createPostReqDTO.getHasImage())
			.build();
	}
}
