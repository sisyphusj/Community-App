package me.sisyphusj.community.app.image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.commons.component.ImageUploadProvider;
import me.sisyphusj.community.app.commons.exception.ImageNotFoundException;
import me.sisyphusj.community.app.image.domain.ImageDetailsInsertVO;
import me.sisyphusj.community.app.image.domain.ImageDetailsResDTO;
import me.sisyphusj.community.app.image.mapper.ImageMapper;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageMapper imageMapper;

	private final ImageUploadProvider imageUploadProvider;

	/**
	 * 디렉토리에 이미지 저장 및 이미지 메타 데이터 DB 저장
	 */
	@Transactional
	public void saveImage(long postId, List<MultipartFile> images) {
		List<ImageDetailsInsertVO> imageDetailsInsertVOList = imageUploadProvider.uploadFiles(images).stream()
			.map(ImageDetailsInsertVO::of)
			.map(imageDetailsInsertVO -> imageDetailsInsertVO.updatePostId(postId))
			.toList();

		imageMapper.insertImages(imageDetailsInsertVOList);
	}

	/**
	 * 이미지 메타 데이터 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<ImageDetailsResDTO> getImages(long postId) {
		return imageMapper.selectImageList(postId).stream()
			.map(ImageDetailsResDTO::of)
			.toList();
	}

	/**
	 * 이미지 제거
	 */
	@Transactional
	public void removeImage(long imageId) {
		String imagePath = imageMapper.selectImagePath(imageId)
			.orElseThrow(ImageNotFoundException::new);

		if (imageMapper.updateImage(SecurityUtil.getLoginUserId(), imageId) != 1) {
			throw new ImageNotFoundException();
		}

		imageUploadProvider.deleteFile(imagePath);
	}
}
