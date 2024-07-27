package me.sisyphusj.community.app.comment_like.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeMapper {

	void insertLikeComment(long userId, long commentId);

	int selectLikeComment(long commentId);

	int selectLikeCommentByUserId(long userId, long commentId);

	void deleteLikeComment(long userId, long commentId);

	void deleteAllLikeComment(List<Long> deleteCommentIdList);
}
