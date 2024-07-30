package me.sisyphusj.community.app.image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.commons.component.ImageUploadProvider;
import me.sisyphusj.community.app.commons.exception.ImageNotFoundException;
import me.sisyphusj.community.app.image.domain.ImageVO;
import me.sisyphusj.community.app.image.domain.PostImageResDTO;
import me.sisyphusj.community.app.image.mapper.ImageMapper;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageMapper imageMapper;

	private final ImageUploadProvider imageUploadProvider;

	/**
	 * 게시글 첨부 이미지를 디렉토리에 이미지 저장 및 이미지 메타 데이터 DB 저장
	 */
	@Transactional
	public void savePostImages(long postId, List<MultipartFile> images) {
		// imageUploadProvider 에서 반환된 이미지 메타데이터 리스트를 DB에 저장
		List<ImageVO> imageVOList = imageUploadProvider.uploadFiles(images).stream()
			.map(ImageVO::of)
			.map(imageVO -> imageVO.updatePostId(postId))
			.toList();

		// 이미지 테이블에 저장
		imageMapper.insertImageList(imageVOList);

		// 게시글 - 이미지 중간 테이블에 정보 저장
		imageMapper.insertPostImageList(imageVOList);
	}

	/**
	 * 썸네일 이미지를 디렉토리에 저장 및 메타 데이터 DB 저장 후 imageId 반환
	 */
	@Transactional
	public long saveThumbnailImage(MultipartFile thumbnail) {
		// imageUploadProvider 에서 반환된 썸네일 이미지 메타데이터를 DB에 저장
		ImageVO imageVO = ImageVO.of(imageUploadProvider.uploadFile(thumbnail));
		imageMapper.insertThumbnailImage(imageVO);

		return imageVO.getImageId();
	}

	/**
	 * 댓글 첨부 이미지를 디렉토리에 이미지 저장 및 이미지 메타 데이터 DB 저장
	 */
	@Transactional
	public void saveCommentImages(long commentId, List<MultipartFile> images) {
		// imageUploadProvider 에서 반환된 이미지 메타데이터 리스트를 DB에 저장
		List<ImageVO> imageVOList = imageUploadProvider.uploadFiles(images).stream()
			.map(ImageVO::of)
			.map(imageVO -> imageVO.updateCommentId(commentId))
			.toList();

		// 이미지 테이블에 저장
		imageMapper.insertImageList(imageVOList);

		// 댓글 - 이미지 중간 테이블에 정보 저장
		imageMapper.insertCommentImageList(imageVOList);
	}

	/**
	 * 게시글 이미지 메타 데이터 리스트 조회
	 */
	@Transactional(readOnly = true)
	public List<PostImageResDTO> getPostImages(long postId) {
		return imageMapper.selectPostImageList(postId).stream()
			.map(PostImageResDTO::of)
			.toList();
	}

	/**
	 * 게시글 이미지 제거
	 */
	@Transactional
	public void removePostImage(long postId, long imageId) {

		// 작성자 확인 및 게시글에 이미지가 존재하는지 확인
		if (imageMapper.selectCountPostImage(SecurityUtil.getLoginUserId(), postId, imageId) != 1) {
			throw new ImageNotFoundException();
		}

		// 이미지 경로를 반환
		String imagePath = imageMapper.selectImagePath(imageId);

		// 이미지 삭제 후 반환 값에 따라 예외 처리
		if (imageMapper.deletePostImage(imageId) != 1) {
			throw new ImageNotFoundException();
		}

		imageUploadProvider.deleteFile(imagePath);
	}

	/**
	 * 댓글 이미지 제거
	 */
	@Transactional
	public void removeCommentImage(long commentId, long imageId) {
		// 작성자 확인 및 댓글에 이미지가 존재하는지 확인
		if (imageMapper.selectCountCommentImage(SecurityUtil.getLoginUserId(), commentId, imageId) != 1) {
			throw new ImageNotFoundException();
		}

		// 이미지 경로를 반환
		String imagePath = imageMapper.selectImagePath(imageId);

		// 이미지 삭제 후 반환 값에 따라 예외 처리
		if (imageMapper.deleteCommentImage(imageId) != 1) {
			throw new ImageNotFoundException();
		}

		imageUploadProvider.deleteFile(imagePath);
	}
}
