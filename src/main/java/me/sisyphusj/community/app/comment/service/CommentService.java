package me.sisyphusj.community.app.comment.service;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.domain.CommentReqDTO;
import me.sisyphusj.community.app.comment.domain.CommentVO;
import me.sisyphusj.community.app.comment.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    /**
     * 댓글 등록
     */
    @Transactional
    public void createComment(CommentReqDTO commentReqDTO) {
        commentMapper.insertComment(CommentVO.of(commentReqDTO));
    }
}
