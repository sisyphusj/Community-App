package me.sisyphusj.community.app.comment.domain;

import lombok.Builder;
import lombok.Getter;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Getter
@Builder
public class CommentVO {

    private Long userId; // 사용자 고유 ID

    private Long postId; // 게시글 고유 ID

    private Long parentId; // 부모 댓글 ID

    private String content; // 댓글 내용

    public static CommentVO of(CommentReqDTO commentReqDTO) {
        return CommentVO.builder()
                .userId(SecurityUtil.getLoginUserId())
                .postId(commentReqDTO.getPostId())
                .parentId(commentReqDTO.getParentId())
                .content(commentReqDTO.getContent())
                .build();
    }
}
