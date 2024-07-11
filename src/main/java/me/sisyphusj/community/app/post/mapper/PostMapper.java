package me.sisyphusj.community.app.post.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.post.domain.PostDetailVO;
import me.sisyphusj.community.app.post.domain.PostSummaryVO;
import me.sisyphusj.community.app.post.domain.PostVO;

@Mapper
public interface PostMapper {

	void insertPost(PostVO postVO);

	List<PostSummaryVO> selectPostSummaryList(int amount, int offset);

	int selectTotalCount();

	Optional<PostDetailVO> selectPostDetails(int postId);

	int updateViewsAndGet(int postId);
}
