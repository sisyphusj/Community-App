package me.sisyphusj.community.app.comment.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentReqDTO {

    @NotNull
    private Long postId; // 게시글 고유 ID

    private Long parentId; // 부모 댓글 ID

    @NotBlank
    private String content; // 댓글 내용

}