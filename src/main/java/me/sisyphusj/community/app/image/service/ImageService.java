package me.sisyphusj.community.app.image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.component.ImageUploadProvider;
import me.sisyphusj.community.app.image.domain.ImageDetailReqDTO;
import me.sisyphusj.community.app.image.domain.ImageDetailResDTO;
import me.sisyphusj.community.app.image.domain.ImageInsertVO;
import me.sisyphusj.community.app.image.domain.ImageUploadReqDTO;
import me.sisyphusj.community.app.image.domain.ImageUploadResDTO;
import me.sisyphusj.community.app.image.mapper.ImageMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageMapper imageMapper;

	private final ImageUploadProvider imageUploadProvider;

	public List<ImageUploadResDTO> uploadImages(ImageUploadReqDTO imageUploadReqDTO) {
		List<MultipartFile> uploadFiles = imageUploadReqDTO.getUploadFiles();
		return imageUploadProvider.uploadFiles(uploadFiles);
	}

	@Transactional
	public void saveImageDetails(int postId, List<ImageDetailReqDTO> imageDetails) {
		List<ImageInsertVO> imageInsertVOList = imageDetails.stream()
			.map(ImageInsertVO::of)
			.map(imageInsertVO -> imageInsertVO.updatePostId(postId))
			.toList();

		imageMapper.insertImages(imageInsertVOList);
	}

	public List<ImageDetailResDTO> getImages(int postId) {

	}
}
