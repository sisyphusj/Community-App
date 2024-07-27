package me.sisyphusj.community.app.post.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.post.domain.PageVO;
import me.sisyphusj.community.app.post.domain.PostVO;

@Mapper
public interface PostMapper {

	void insertPost(PostVO postVO);

	List<PostVO> selectPostSummaryList(PageVO pageVO);

	int selectTotalCount();

	Optional<PostVO> selectPostDetails(long postId);

	Optional<PostVO> selectPostDetailsByUserId(long userId, long postId);

	int updateViewsAndGet(long postId);

	int selectCountPostByUserId(long postId, long userId);

	int selectCountPost(long postId);

	void updatePost(PostVO postVO);

	void deletePost(long postId);

	void deletePostImage(long postId);

}
