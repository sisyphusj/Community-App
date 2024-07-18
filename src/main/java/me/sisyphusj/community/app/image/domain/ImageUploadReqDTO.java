package me.sisyphusj.community.app.image.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageUploadReqDTO {

	@NotEmpty
	private List<MultipartFile> uploadFiles; // 이미지 파일들

}
