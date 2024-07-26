package me.sisyphusj.community.app.comment.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.comment.domain.CommentVO;

@Mapper
public interface CommentMapper {

	void insertComment(CommentVO commentVO);

	int selectCountCommentByPostId(long postId);

	int selectCountCommentByCommentId(long commentId);

	List<CommentVO> selectCommentList(long postId, boolean isAscending);

	List<CommentVO> selectCommentListByUserId(long userId, long postId, boolean isAscending);

	Optional<CommentVO> selectComment(long userId, long commentId);

	void editComment(CommentVO commentEditVO);

	void deleteComment(List<Long> deleteCommentIdList);

	void deleteCommentImage(List<Long> deleteCommentIdList);

}
