package me.sisyphusj.community.app.utils;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			if (file.getSize() == 0 || file.getOriginalFilename() == null) {
				return false;
			}
		}

		return true;
	}
}
