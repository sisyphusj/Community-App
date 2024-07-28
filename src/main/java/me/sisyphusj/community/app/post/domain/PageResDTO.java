package me.sisyphusj.community.app.post.domain;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.List;

import lombok.Getter;

@Getter
public class PageResDTO {

	private final int currentPage; // 현재 페이지 번호

	private final int rowSizePerPage; // 페이지 당 게시글 수

	private int pageSize; // 페이지 버튼 개수

	private final int totalRowCount; // 전체 게시글 개수

	private int firstRow; // 시작 레코드(게시글) 번호

	private int lastRow; // 마지막 레코드 번호

	private int totalPageCount; // 총 페이지 수 (마지막 페이지 번호)

	private int firstPage; // 페이지 리스트에서 시작 번호

	private int lastPage; // 페이지 리스트에서 마지막 페이지 번호

	private List<PostSummaryResDTO> postSummaryResDTO; // 게시글 요약 정보 리스트

	private void pageSetting() {

		// 총 페이지 수 계산
		totalPageCount = (totalRowCount - 1) / rowSizePerPage + 1;

		// 페이지 버튼 개수 설정
		pageSize = Math.min(PAGE_SIZE, totalPageCount);

		// 시작 레코드 번호 계산
		firstRow = (currentPage - 1) * rowSizePerPage + 1;

		// 마지막 레코드 번호 계산
		lastRow = Math.min(firstRow + rowSizePerPage - 1, totalRowCount);

		// 페이지 리스트에서 시작 번호 계산
		firstPage = ((currentPage - 1) / pageSize) * pageSize + 1;

		// 페이지 리스트에서 마지막 번호 계산
		lastPage = Math.min(firstPage + pageSize - 1, totalPageCount);
	}

	public PageResDTO(PageReqDTO pageReqDTO, int totalRowCount, List<PostSummaryResDTO> postSummaryResDTO) {
		this.currentPage = pageReqDTO.getPage();
		this.rowSizePerPage = pageReqDTO.getRow();
		this.pageSize = PAGE_SIZE;
		this.totalRowCount = totalRowCount;
		this.postSummaryResDTO = postSummaryResDTO;

		pageSetting();
	}
}
