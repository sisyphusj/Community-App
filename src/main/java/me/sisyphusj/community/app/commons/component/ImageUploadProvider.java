package me.sisyphusj.community.app.commons.component;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.io.File;
import java.io.IOException;
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

import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.exception.FileUploadException;
import me.sisyphusj.community.app.image.domain.ImageMetadata;

@Slf4j
@Component
public class ImageUploadProvider {

	@Value("${file.upload-path}")
	private String uploadPath; // 이미지 업로드 기본 경로

	/**
	 * 이미지 리스트를 반복을 통해 로컬 디렉토리에 업로드 <br> 이미지 메타데이터 리스트 반환
	 */
	public List<ImageMetadata> uploadFiles(List<MultipartFile> multipartFiles) {
		List<ImageMetadata> files = new ArrayList<>(); // 이미지 메타데이터 응답 리스트

		for (MultipartFile multipartFile : multipartFiles) {
			if (multipartFile.isEmpty()) {
				continue;
			}
			files.add(uploadFile(multipartFile));
		}
		return files;
	}

	public void deleteFile(String imagePath) {
		String path = Paths.get("C:/communityImages", imagePath.replace("/uploads", "")).toString();
		File file = new File(path);
		if (!file.exists()) {
			throw new IllegalArgumentException("파일이 존재하지 않습니다.");
		}
		file.delete();
	}

	/**
	 * 이미지 파일 업로드
	 */
	private ImageMetadata uploadFile(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new IllegalArgumentException("파일이 비어있습니다.");
		}

		String storedName = generateStoredFileName(multipartFile.getOriginalFilename()); // 실제 디렉토리에 저장되는 이름
		String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")); // 당일 날짜를 디렉토리 명으로 선언 
		String newUploadPath = Paths.get(getUploadPath(today), storedName).toString(); // 최종 경로
		File uploadFile = new File(newUploadPath); // 파일 객체 생성

		try {
			multipartFile.transferTo(uploadFile); // 지정된 경로에 파일 저장
		} catch (IOException e) {
			log.error("[파일 경로 업로드 에러] : {} ", multipartFile.getOriginalFilename(), e);
			throw new FileUploadException();
		}

		// 메타데이터 반환
		return ImageMetadata.builder()
			.originalName(multipartFile.getOriginalFilename())
			.storedName(storedName)
			.imagePath(String.format("/uploads/%s/%s", today, storedName))
			.size(multipartFile.getSize())
			.build();
	}

	/**
	 * UUID로 이미지 파일명 변경
	 */
	private String generateStoredFileName(String filename) {
		String uuid = UUID.randomUUID().toString();
		String extension = StringUtils.getFilenameExtension(filename); // 확장자 추출

		// 허용된 이미지 확장자가 아니면 예외 처리
		if (extension == null || !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
			throw new IllegalArgumentException(String.format("허용되지 않는 파일 형식입니다. : %s", extension));
		}

		return String.format("%s.%s", uuid, extension);
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
