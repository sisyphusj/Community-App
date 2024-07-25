package me.sisyphusj.community.app.image.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.image.domain.ImageVO;

@Mapper
public interface ImageMapper {

	void insertImageList(List<ImageVO> images);

	void insertPostImageList(List<ImageVO> images);

	void insertCommentImageList(List<ImageVO> images);

	List<ImageVO> selectPostImageList(long postId);

	List<ImageVO> selectCommentImageList(long commentId);

	int selectCountPostImage(long userId, long postId, long imageId);

	int selectCountCommentImage(long userId, long commentId, long imageId);

	Optional<String> selectImagePath(long imageId);

	int deletePostImage(long imageId);

	int deleteCommentImage(long imageId);

}
