package me.sisyphusj.community.app.post.domain;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SessionUtil;

@Builder
@Getter
public class PostVO {

	private int postId;

	private int userId;

	private String title;

	private String content;

	private HasImage hasImage;

	public static PostVO of(CreatePostReqDTO createPostReqDTO) {
		return PostVO.builder()
			.userId(SessionUtil.getLoginUserId())
			.title(createPostReqDTO.getTitle())
			.content(createPostReqDTO.getContent())
			.hasImage(createPostReqDTO.getHasImage())
			.build();
	}
}
