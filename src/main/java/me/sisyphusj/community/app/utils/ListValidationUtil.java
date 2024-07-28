package me.sisyphusj.community.app.utils;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ListValidationUtil {

	private ListValidationUtil() {
	}

	/**
	 * 이미지 파일 리스트가 null이 아니고 비어있지 않은지 검증
	 */
	public static boolean isValidMultiPartFileList(List<MultipartFile> list) {
		if (list == null || list.isEmpty()) {
			return false;
		}

		for (MultipartFile file : list) {
			if (!isValidMultipartFile(file)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 이미지 파일이 비어있는지 검증
	 */
	public static boolean isValidMultipartFile(MultipartFile file) {
		return file != null && file.getSize() != 0 && file.getOriginalFilename() != null;
	}
}
