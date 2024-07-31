package me.sisyphusj.community.app.commons.component;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.exception.FileUploadException;
import me.sisyphusj.community.app.image.domain.ImageMetadata;
import me.sisyphusj.community.app.image.domain.ImageType;

@Slf4j
@Component
public class ImageUploadProvider {

	@Value("${file.upload-path}")
	private String uploadPath; // 이미지 업로드 기본 경로

	/**
	 * 이미지 리스트를 반복을 통해 로컬 디렉토리에 업로드 <br> 이미지 메타데이터 리스트 반환
	 */
	public List<ImageMetadata> uploadFiles(List<MultipartFile> multipartFiles, ImageType imageType) {
		List<ImageMetadata> files = new ArrayList<>(); // 이미지 메타데이터 응답 리스트

		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile.isEmpty()) {
				continue;
			}
			files.add(uploadFile(multipartFile, imageType));
		}
		return files;
	}

	/**
	 * 로컬 디렉토리에서 지정된 이미지 삭제
	 */
	public void deleteFile(String imagePath) {
		String path = Paths.get(uploadPath, imagePath.replace("/uploads", "")).toString();
		File file = new File(path);
		if (!file.exists()) {
			throw new IllegalArgumentException("파일이 존재하지 않습니다.");
		}
		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			throw new FileUploadException();
		}
	}

	/**
	 * 이미지 파일 업로드
	 */
	public ImageMetadata uploadFile(MultipartFile multipartFile, ImageType imageType) {
		// 파일이 비어있으면 예외
		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException("파일이 비어있습니다.");
		}

		String storedName = generateStoredFileName(multipartFile.getOriginalFilename()); // 실제 디렉토리에 저장되는 이름
		String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")); // 당일 날짜를 디렉토리 명으로 선언
		File uploadFile = new File(Paths.get(getUploadPath(today), storedName).toString()); // 파일 객체 생성

		if (imageType == ImageType.THUMBNAIL) {
			// 썸네일 이미지면 리사이징 후 업로드
			resizeAndUploadThumbnail(multipartFile, uploadFile);
		} else {
			// 일반 이미지 업로드
			uploadNormalImage(multipartFile, uploadFile);
		}

		// 메타데이터 반환
		return ImageMetadata.builder()
			.originName(multipartFile.getOriginalFilename())
			.storedName(storedName)
			.imagePath("/uploads/" + today + "/" + storedName)
			.size(multipartFile.getSize())
			.build();
	}

	/**
	 * 일반 첨부 이미지 업로드
	 *
	 * @param multipartFile 이미지 파일
	 * @param uploadFile 저장 경로가 지정된 대상 파일 객체
	 */
	private void uploadNormalImage(MultipartFile multipartFile, File uploadFile) {
		try {
			multipartFile.transferTo(uploadFile); // 지정된 경로에 파일 저장
		} catch (IOException e) {
			log.error("[파일 경로 업로드 에러] : {} ", multipartFile.getOriginalFilename(), e);
			throw new FileUploadException();
		}
	}

	/**
	 * 썸네일 이미지 리사이징 후 업로드
	 *
	 * @param multipartFile 이미지 파일
	 * @param uploadFile 저장 경로가 지정된 대상 파일 객체
	 */
	private void resizeAndUploadThumbnail(MultipartFile multipartFile, File uploadFile) {
		try {
			Thumbnails.of(multipartFile.getInputStream())
				.size(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT) // 150,150으로 리사이징 크기 지정
				.outputFormat("png") // 파일 확장자 지정
				.outputQuality(0.8) // 압축 품질 80%
				.toFile(uploadFile);
		} catch (IOException e) {
			log.error("[파일 경로 업로드 에러] : {} ", multipartFile.getOriginalFilename(), e);
			throw new FileUploadException();
		}
	}

	/**
	 * UUID로 이미지 파일명 변경
	 */
	private String generateStoredFileName(String filename) {
		String uuid = UUID.randomUUID().toString();
		String extension = StringUtils.getFilenameExtension(filename); // 확장자 추출

		// 허용된 이미지 확장자가 아니면 예외 처리
		if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
			throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. : " + extension);
		}

		return uuid + '.' + extension;
	}

	/**
	 * 날짜명으로 된 디렉토리 반환
	 */
	private String getUploadPath(String addPath) {
		return makeDirectories(Paths.get(uploadPath, addPath).toString());
	}

	/**
	 * 디렉토리 생성 / 존재하면 경로 반환
	 */
	private String makeDirectories(String path) {
		File file = new File(path); // 디렉토리로 간주

		if (!file.exists() && !file.mkdirs()) {
			log.error("[디렉토리 생성 실패] : {}", path);
			throw new FileUploadException();
		}

		return file.getPath();
	}
}
