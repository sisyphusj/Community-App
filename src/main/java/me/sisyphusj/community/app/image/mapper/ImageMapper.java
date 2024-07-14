package me.sisyphusj.community.app.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.image.domain.ImageInsertVO;
import me.sisyphusj.community.app.image.domain.ImageSelectVO;

@Mapper
public interface ImageMapper {

	void insertImages(List<ImageInsertVO> images);

	List<ImageSelectVO> selectImageList(int postId);

}
