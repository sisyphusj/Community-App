package me.sisyphusj.community.app.post.service;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.exception.PostNotFoundException;
import me.sisyphusj.community.app.post.domain.CreatePostReqDTO;
import me.sisyphusj.community.app.post.domain.PageResDTO;
import me.sisyphusj.community.app.post.domain.PostDetailResDTO;
import me.sisyphusj.community.app.post.domain.PostSummaryResDTO;
import me.sisyphusj.community.app.post.domain.PostVO;
import me.sisyphusj.community.app.post.mapper.PostMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostMapper postMapper;

	/**
	 * 게시글 생성
	 */
	@Transactional
	public void createdPost(CreatePostReqDTO createPostReqDTO) {
		postMapper.insertPost(PostVO.of(createPostReqDTO));
	}

	/**
	 * 한 페이지 당 조회될 게시글 리스트 및 페이지 정보 반환
	 */
	@Transactional(readOnly = true)
	public PageResDTO getPostPage(int currentPage) {
		//TODO 페이지당 게시글 수 변경 기능 추가

		// 전체 게시글 개수
		int totalRowCount = postMapper.selectTotalCount();

		// 마지막으로 호출된 row
		int offset = (currentPage - 1) * ROW_SIZE_PER_PAGE;

		// amount 만큼 게시글 리스트 조회
		List<PostSummaryResDTO> postListDTO = postMapper.selectPostSummaryList(ROW_SIZE_PER_PAGE, offset).stream()
			.map(PostSummaryResDTO::of)
			.toList();

		// 현재 페이지 페이지네이션 메타데이터, 게시글 섬네일 리스트 반환
		return new PageResDTO(currentPage, ROW_SIZE_PER_PAGE, totalRowCount, postListDTO);
	}

	/**
	 * postId를 통한 게시글 조회
	 */
	@Transactional
	public PostDetailResDTO getPostDetails(int postId) {
		if (postMapper.updateViewsAndGet(postId) == 0) {
			throw new PostNotFoundException();
		}

		return postMapper.selectPostDetails(postId)
			.map(PostDetailResDTO::of)
			.orElseThrow(PostNotFoundException::new);
	}
}
