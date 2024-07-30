package me.sisyphusj.community.app.comment.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sisyphusj.community.app.post.domain.BoardType;

@Getter
@AllArgsConstructor
public class CommentReqDTO {

	@NotNull
	private Long postId; // 게시글 고유 ID

	private Long parentId; // 부모 댓글 ID

	@NotBlank
	private String content; // 댓글 내용

	private List<MultipartFile> images; // 추가된 이미지 리스트

	@NotNull
	BoardType boardType; // 게시판 타입

}
