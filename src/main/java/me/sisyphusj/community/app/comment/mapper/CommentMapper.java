package me.sisyphusj.community.app.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.comment.domain.CommentDetailVO;
import me.sisyphusj.community.app.comment.domain.CommentVO;

@Mapper
public interface CommentMapper {

	void insertComment(CommentVO commentVO);

	int selectCountComment(long postId);

	List<CommentDetailVO> selectComment(long postId);
}
