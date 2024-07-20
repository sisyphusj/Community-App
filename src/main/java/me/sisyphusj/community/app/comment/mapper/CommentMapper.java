package me.sisyphusj.community.app.comment.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.comment.domain.CommentDetailVO;
import me.sisyphusj.community.app.comment.domain.CommentEditVO;
import me.sisyphusj.community.app.comment.domain.CommentVO;
import me.sisyphusj.community.app.comment.domain.HasChild;

@Mapper
public interface CommentMapper {

	void insertComment(CommentVO commentVO);

	int selectCountComment(long postId);

	List<CommentDetailVO> selectCommentList(long postId);

	Optional<CommentDetailVO> selectComment(long userId, long commentId);

	void updateCommentHasChild(long commentId, HasChild hasChild);

	int selectCountCommentByUserIdAndCommentId(long userId, long commentId);

	void editComment(CommentEditVO commentEditVO);

	void deleteChildComment(long parentId);

	void deleteComment(long userId, long commentId);

	int selectCountChildComment(long parentId);

}
