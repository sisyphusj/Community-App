package me.sisyphusj.community.app.post.domain;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.List;

import lombok.Getter;

@Getter
public class PageResDTO {

	private final int currentPage; // 현재 페이지 번호

	private final int rowSizePerPage; // 페이지 당 게시글 수

	private final int pageSize; // 페이지 버튼 개수

	private final int totalRowCount; // 전체 게시글 개수

	private int firstRow; // 시작 레코드(게시글) 번호

	private int lastRow; // 마지막 레코드 번호

	private int totalPageCount; // 총 페이지 수 (마지막 페이지 번호)

	private int firstPage; // 페이지 리스트에서 시작 번호

	private int lastPage; // 페이지 리스트에서 마지막 페이지 번호

	private List<PostSummaryResDTO> postSummaryResDTO;

	private void pageSetting() {

		totalPageCount = (totalRowCount - 1) / rowSizePerPage + 1;

		firstRow = (currentPage - 1) * rowSizePerPage + 1;

		lastRow = firstRow + rowSizePerPage - 1;

		// rowSizePerPage 만큼 리스트를 보여줄 수 없는 경우
		if (lastRow > totalRowCount)
			lastRow = totalRowCount;

		firstPage = (currentPage - 1) / pageSize * pageSize + 1;

		lastPage = firstPage + pageSize - 1;

		// pageSize 만큼의 페이지 버튼을 보여줄 수 없는 경우
		if (lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}
	}

	public PageResDTO(int currentPage, int totalRowCount, List<PostSummaryResDTO> postSummaryResDTO) {
		this.currentPage = currentPage;
		this.rowSizePerPage = ROW_SIZE_PER_PAGE;
		this.pageSize = PAGE_SIZE;
		this.totalRowCount = totalRowCount;
		this.postSummaryResDTO = postSummaryResDTO;

		pageSetting();
	}
}
