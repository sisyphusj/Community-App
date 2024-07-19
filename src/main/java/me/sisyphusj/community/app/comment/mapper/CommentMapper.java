package me.sisyphusj.community.app.comment.mapper;

import me.sisyphusj.community.app.comment.domain.CommentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    void insertComment(CommentVO commentVO);

}
