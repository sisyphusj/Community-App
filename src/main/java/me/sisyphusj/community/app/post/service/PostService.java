package me.sisyphusj.community.app.post.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.commons.exception.CategoryNotFoundException;
import me.sisyphusj.community.app.commons.exception.KeywordTypeException;
import me.sisyphusj.community.app.commons.exception.PostNotFoundException;
import me.sisyphusj.community.app.image.service.ImageService;
import me.sisyphusj.community.app.post.domain.BoardType;
import me.sisyphusj.community.app.post.domain.KeywordType;
import me.sisyphusj.community.app.post.domain.PageReqDTO;
import me.sisyphusj.community.app.post.domain.PageResDTO;
import me.sisyphusj.community.app.post.domain.PageVO;
import me.sisyphusj.community.app.post.domain.PostCreateReqDTO;
import me.sisyphusj.community.app.post.domain.PostDetailResDTO;
import me.sisyphusj.community.app.post.domain.PostEditReqDTO;
import me.sisyphusj.community.app.post.domain.PostSummaryResDTO;
import me.sisyphusj.community.app.post.domain.PostVO;
import me.sisyphusj.community.app.post.mapper.PostMapper;
import me.sisyphusj.community.app.post_like.mapper.PostLikeMapper;
import me.sisyphusj.community.app.utils.ListValidationUtil;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;

	private final ImageService imageService;

	private final PostLikeMapper postLikeMapper;

	/**
	 * 게시글 생성
	 */
	@Transactional
	public void createPost(PostCreateReqDTO postCreateReqDTO) {
		PostVO postVO = PostVO.of(postCreateReqDTO);

		// (카테고리 : 게시판 타입) 유효성 체크 후 카테고리 ID를 VO에 저장
		postVO.updateBoardCategoryId(getBoardCategoryId(postCreateReqDTO.getCategory(), postCreateReqDTO.getBoardType()));

		// 썸네일 이미지가 존재하면 이미지 저장 후 썸네일 이미지 ID을 VO에 저장
		handleThumbnailImage(postCreateReqDTO.getBoardType(), postCreateReqDTO.getThumbnail(), postVO);

		postMapper.insertPost(postVO);

		// 게시글이 이미지를 첨부, 이미지 리스트가 NULL이 아니면 이미지 저장 요청
		if (ListValidationUtil.isValidMultiPartFileList(postCreateReqDTO.getImages())) {
			imageService.savePostImages(postVO.getPostId(), postCreateReqDTO.getImages());
		}
	}

	/**
	 * 한 페이지 당 조회될 게시글 리스트 및 페이지 정보 반환
	 */
	@Transactional(readOnly = true)
	public PageResDTO getBoardPage(BoardType boardType, PageReqDTO pageReqDTO) {

		// 검색 keyword가 존재한다면 keywordType 확인
		validateKeywordType(pageReqDTO.getKeyword(), pageReqDTO.getKeywordType());

		PageVO pageVO = PageVO.of(pageReqDTO);

		// 게시판 타입 지정
		pageVO.updateBoardType(boardType);

		// 전체 게시글 개수
		int totalRowCount = postMapper.selectPostTotalCount(pageVO);

		// amount 만큼 게시글 리스트 조회
		List<PostSummaryResDTO> postListDTO = postMapper.selectPostSummaryList(pageVO).stream()
			.map(PostSummaryResDTO::of)
			.toList();

		// 현재 페이지 페이지네이션 메타데이터, 게시글 섬네일 리스트 반환
		return new PageResDTO(pageReqDTO, totalRowCount, postListDTO);
	}

	/**
	 * postId를 통한 게시글 조회
	 */
	@Transactional
	public PostDetailResDTO getPostDetails(long postId, BoardType boardType) {
		// 게시글 유효 체크 및 조회 수 갱신
		updateViews(postId);

		// 게시판 타입에 따라서 게시글 요청
		return handlePostDetails(postId, boardType);
	}

	/**
	 * 게시글 수정
	 */
	@Transactional
	public void editPost(PostEditReqDTO postEditReqDTO) {
		// 게시글의 존재 여부 확인
		validatePostExists(postEditReqDTO.getPostId());

		PostVO postVO = PostVO.of(postEditReqDTO);

		// 썸네일 이미지가 존재하면 이미지 저장 후 썸네일 이미지 ID을 VO에 저장
		handleThumbnailImage(postEditReqDTO.getBoardType(), postEditReqDTO.getThumbnail(), postVO);

		// 게시글 갱신
		postMapper.updatePost(postVO);

		// 게시글이 이미지를 첨부, 이미지 리스트가 NULL이 아니면 이미지 저장 요청
		if (ListValidationUtil.isValidMultiPartFileList(postEditReqDTO.getImages())) {
			imageService.savePostImages(postVO.getPostId(), postEditReqDTO.getImages());
		}
	}

	/**
	 * 게시글 삭제
	 */
	@Transactional
	public void removePost(long postId) {
		// 게시글 존재 여부 확인
		validatePostExists(postId);

		// 게시글 삭제
		postMapper.deletePost(postId);

		// 게시글 - 이미지 중간 테이블 정보 삭제
		postMapper.deletePostImage(postId);

		// 게시글 좋아요 테이블 정보 삭제
		postLikeMapper.deleteAllLikePost(postId);
	}

	/**
	 * 게시글 유효 체크 및 조회 수 갱신
	 */
	private void updateViews(long postId) {
		if (postMapper.updateViewsAndGet(postId) == 0) {
			throw new PostNotFoundException();
		}
	}

	/**
	 * KeywordType 유효성 체크
	 */
	private void validateKeywordType(String keyword, KeywordType keywordType) {
		// 검색어는 존재하지만 keywordType은 null일 경우 예외 발생
		if (StringUtils.isNotBlank(keyword) && keywordType == null) {
			throw new KeywordTypeException();
		}
	}

	/**
	 * 게시글의 존재 여부를 확인
	 */
	private void validatePostExists(long postId) {
		// 게시글 존재 여부, 해당 게시글의 작성자가 사용자인지 확인
		if (postMapper.selectCountPostByUserId(SecurityUtil.getLoginUserId(), postId) != 1) {
			throw new PostNotFoundException();
		}
	}

	/**
	 * 카테고리의 유효성 확인 후 카테고리 고유 ID 반환
	 */
	private long getBoardCategoryId(String category, BoardType boardType) {
		Long boardCategoryId = postMapper.selectCategoryId(category, boardType);

		// 게시판의 타입에 따른 카테고리를 조회하여 카테고리 ID가 NULL이면 예외
		if (boardCategoryId == null) {
			throw new CategoryNotFoundException();
		}

		return boardCategoryId;
	}

	/**
	 * 게시글 수정 시 썸네일 이미지 저장 및 이미지 ID VO에 추가
	 */
	private void handleThumbnailImage(BoardType boardType, MultipartFile thumbnail, PostVO postVO) {
		if (boardType == BoardType.GALLERY && ListValidationUtil.isValidMultipartFile(thumbnail)) {
			postVO.updateThumbnailId(imageService.saveThumbnailImage(thumbnail));
		}
	}

	/**
	 * 일반/갤러리 게시판 게시글 조회
	 */
	private PostDetailResDTO handlePostDetails(long postId, BoardType boardType) {
		// 사용자가 로그인 사용자면 이미지 게시글 좋아요 여부를 포함하여 게시글 조회
		if (SecurityUtil.isLoginUser()) {
			return postMapper.selectPostDetailsByUserId(SecurityUtil.getLoginUserId(), postId, boardType)
				.map(PostDetailResDTO::of)
				.orElseThrow(PostNotFoundException::new);
		}

		// 사용자가 미 로그인 사용자면 좋아요 여부는 조회하지 않고 이미지 게시글 조회
		return postMapper.selectPostDetails(postId, boardType)
			.map(PostDetailResDTO::of)
			.orElseThrow(PostNotFoundException::new);
	}
}
