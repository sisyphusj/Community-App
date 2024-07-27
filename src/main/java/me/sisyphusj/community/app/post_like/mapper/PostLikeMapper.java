package me.sisyphusj.community.app.post_like.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {

	void insertLikePost(long userId, long postId);

	int selectLikePost(long postId);

	int selectLikePostByUserId(long userId, long postId);

	void deleteLikePost(long userId, long postId);

	void deleteAllLikePost(long postId);

}
