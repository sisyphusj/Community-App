package me.sisyphusj.community.app.image.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.image.domain.ImageDetailsInsertVO;
import me.sisyphusj.community.app.image.domain.ImageDetailsSelectVO;

@Mapper
public interface ImageMapper {

	void insertImages(List<ImageDetailsInsertVO> images);

	List<ImageDetailsSelectVO> selectImageList(long postId);

	Optional<String> selectImagePath(long imageId);

	int deleteImage(long userId, long imageId);

}
