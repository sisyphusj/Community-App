package me.sisyphusj.community.app.like.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {

	void insertLikePost(long userId, long postId);

	void insertLikeComment(long userId, long commentId);

	int selectLikePost(long postId);

	int selectLikeComment(long commentId);

	int selectLikePostByUserId(long userId, long postId);

	int selectLikeCommentByUserId(long userId, long commentId);

	void deleteLikePost(long userId, long postId);

	void deleteAllLikePost(long postId);

	void deleteLikeComment(long userId, long commentId);

	void deleteAllLikeComment(List<Long> deleteCommentIdList);
}
