package me.sisyphusj.community.app.like.mapper;

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

	void deleteLikeComment(long userId, long commentId);
}
