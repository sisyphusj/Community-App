package me.sisyphusj.community.app.comment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.domain.CommentDetailResDTO;
import me.sisyphusj.community.app.comment.domain.CommentDetailVO;
import me.sisyphusj.community.app.comment.domain.CommentReqDTO;
import me.sisyphusj.community.app.comment.domain.CommentVO;
import me.sisyphusj.community.app.comment.domain.HasChild;
import me.sisyphusj.community.app.comment.mapper.CommentMapper;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentMapper commentMapper;

	/**
	 * 댓글 등록
	 */
	@Transactional
	public void createComment(CommentReqDTO commentReqDTO) {
		commentMapper.insertComment(CommentVO.of(commentReqDTO));
	}

	/**
	 * 댓글 존재 유무 확인
	 */
	@Transactional
	public boolean hasComment(long postId) {
		return commentMapper.selectCountComment(postId) > 0;
	}

	/**
	 * 댓글 리스트 조회 (재귀)
	 */
	@Transactional
	public List<CommentDetailResDTO> getCommentListUseRecursion(long postId) {
		List<CommentDetailVO> comments = commentMapper.selectComment(postId); // 최신순으로 정렬된 댓글 리스트
		List<CommentDetailVO> newComments = new ArrayList<>();

		Map<Long, List<CommentDetailVO>> childrenMap = new HashMap<>(); // [댓글 ID, 자식 댓글 리스트] 쌍으로 저장된 해시 맵

		for (CommentDetailVO comment : comments) {
			if (comment.getParentId() != null) {
				childrenMap.computeIfAbsent(comment.getParentId(), k -> new ArrayList<>()).add(comment);
			}
		}

		// 최상위 부모 댓글 리스트 생성
		List<CommentDetailVO> rootComments = comments.stream()
			.filter(comment -> comment.getParentId() == null)
			.toList();

		// 최상위 부모 댓글에서 자식 댓글 조회 
		for (CommentDetailVO comment : rootComments) {
			newComments.add(comment);
			if (comment.getHasChild() == HasChild.Y) {
				getComments(comment, childrenMap, newComments);
			}
		}

		return newComments.stream()
			.map(CommentDetailResDTO::of)
			.toList();
	}

	/**
	 * 현재 댓글 추가 및 자식 댓글 조회 
	 * @param currentComment 현재 댓글 객체
	 * @param childrenMap 자식 댓글 해시 맵
	 * @param newComments 최종 저장되는 댓글 리스트
	 */
	private void getComments(CommentDetailVO currentComment, Map<Long, List<CommentDetailVO>> childrenMap, List<CommentDetailVO> newComments) {
		List<CommentDetailVO> children = childrenMap.get(currentComment.getCommentId()); // 현재 댓글의 자식 댓글 리스트

		for (CommentDetailVO comment : children) {
			newComments.add(comment);
			if (comment.getHasChild() == HasChild.Y) {
				getComments(comment, childrenMap, newComments);
			}
		}
	}
}
