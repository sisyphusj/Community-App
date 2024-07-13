package me.sisyphusj.community.app.image.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import me.sisyphusj.community.app.image.domain.ImageVO;

@Mapper
public interface ImageMapper {

	void insertImages(List<ImageVO> images);
}
