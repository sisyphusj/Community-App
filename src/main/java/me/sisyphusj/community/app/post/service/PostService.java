package me.sisyphusj.community.app.post.service;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.commons.exception.PostNotFoundException;
import me.sisyphusj.community.app.image.service.ImageService;
import me.sisyphusj.community.app.post.domain.HasImage;
import me.sisyphusj.community.app.post.domain.PageResDTO;
import me.sisyphusj.community.app.post.domain.PageSortType;
import me.sisyphusj.community.app.post.domain.PostCreateReqDTO;
import me.sisyphusj.community.app.post.domain.PostDetailResDTO;
import me.sisyphusj.community.app.post.domain.PostEditReqDTO;
import me.sisyphusj.community.app.post.domain.PostSummaryResDTO;
import me.sisyphusj.community.app.post.domain.PostVO;
import me.sisyphusj.community.app.post.mapper.PostMapper;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;

	private final ImageService imageService;

	/**
	 * 게시글 생성
	 */
	@Transactional
	public void createPost(PostCreateReqDTO postCreateReqDTO) {
		PostVO postVO = PostVO.of(postCreateReqDTO);
		postMapper.insertPost(postVO); // 게시글 삽입

		// 게시글이 이미지를 첨부, 이미지 리스트가 NULL이 아니면 이미지 저장 요청
		if (postVO.getHasImage() == HasImage.Y && !(postCreateReqDTO.getImages().isEmpty())) {
			imageService.saveImage(postVO.getPostId(), postCreateReqDTO.getImages());
		}
	}

	/**
	 * 한 페이지 당 조회될 게시글 리스트 및 페이지 정보 반환
	 */
	@Transactional(readOnly = true)
	public PageResDTO getPostPage(int currentPage, PageSortType pageSortType) {
		//TODO 페이지당 게시글 수 변경 기능 추가

		// 전체 게시글 개수
		int totalRowCount = postMapper.selectTotalCount();

		// 마지막으로 호출된 row
		int offset = (currentPage - 1) * ROW_SIZE_PER_PAGE;

		// amount 만큼 게시글 리스트 조회
		List<PostSummaryResDTO> postListDTO = postMapper.selectPostSummaryList(ROW_SIZE_PER_PAGE, offset, pageSortType).stream()
			.map(PostSummaryResDTO::of)
			.toList();

		// 현재 페이지 페이지네이션 메타데이터, 게시글 섬네일 리스트 반환
		return new PageResDTO(currentPage, totalRowCount, postListDTO);
	}

	/**
	 * postId를 통한 게시글 조회
	 */
	@Transactional
	public PostDetailResDTO getPostDetails(long postId) {
		// 조회 수 갱신 후 반환 값에 따라 예외 처리
		if (postMapper.updateViewsAndGet(postId) == 0) {
			throw new PostNotFoundException();
		}

		// 이미지 메타데이터
		return postMapper.selectPostDetails(postId)
			.map(PostDetailResDTO::of)
			.orElseThrow(PostNotFoundException::new);
	}

	/**
	 * 게시글 수정
	 */
	@Transactional
	public void editPost(PostEditReqDTO postEditReqDTO) {
		// 게시글의 존재 여부 확인
		if (postMapper.selectCountPost(postEditReqDTO.getPostId(), SecurityUtil.getLoginUserId()) != 1) {
			throw new PostNotFoundException();
		}

		// 게시글 갱신
		PostVO postVO = PostVO.of(postEditReqDTO);
		postMapper.updatePost(postVO);

		// 게시글이 이미지를 첨부, 이미지 리스트가 NULL이 아니면 이미지 저장 요청
		if (postVO.getHasImage() == HasImage.Y && (postEditReqDTO.getImages() != null)) {
			imageService.saveImage(postVO.getPostId(), postEditReqDTO.getImages());
		}
	}

	/**
	 * 게시글 삭제
	 */
	@Transactional
	public void removePost(long postId) {
		// 게시글 존재 여부 확인
		if (postMapper.selectCountPost(postId, SecurityUtil.getLoginUserId()) != 1) {
			throw new PostNotFoundException();
		}

		// 게시글 삭제
		postMapper.deletePost(postId);
	}
}
