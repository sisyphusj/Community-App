package me.sisyphusj.community.app.image.domain;

import java.util.Date;

import lombok.Getter;

@Getter
public class ImageDetailsSelectVO {

	private long imageId; // 이미지 고유 ID

	private long postId; // 게시글 고유 ID

	private String originalName; // 파일 원본 이름

	private String storedName; // 저장소에 저장된 파일의 이름

	private long size; // 이미지 크기

	private String imagePath; // 저장소의 Image 주소

	private Date createdDate; // 이미지 업로드 날짜

}
