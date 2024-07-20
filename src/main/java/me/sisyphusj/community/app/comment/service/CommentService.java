package me.sisyphusj.community.app.comment.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		// 부모 댓글 ID 존재 시 갱신 요청
		if (commentReqDTO.getParentId() != null) {
			commentMapper.updateCommentHasChild(commentReqDTO.getParentId(), HasChild.Y);
		}

		commentMapper.insertComment(CommentVO.of(commentReqDTO));
	}

	/**
	 * 댓글 존재 유무 확인
	 */
	@Transactional(readOnly = true)
	public boolean hasComment(long postId) {
		return commentMapper.selectCountComment(postId) > 0;
	}

	/**
	 * 댓글 리스트 조회 (재귀)
	 */
	@Transactional(readOnly = true)
	public List<CommentDetailResDTO> getCommentListUseRecursion(long postId) {
		// 최신순으로 정렬된 댓글 리스트 가져오기
		List<CommentDetailVO> comments = commentMapper.selectCommentOrderByDesc(postId);

		// 최종 저장되는 댓글 리스트 생성
		List<CommentDetailVO> newComments = new ArrayList<>();

		// 댓글 ID, 자식 댓글 리스트를 매핑하는 해시 맵 생성
		Map<Long, List<CommentDetailVO>> childrenMap = comments.stream()
			.filter(comment -> comment.getParentId() != null)
			.collect(Collectors.groupingBy(CommentDetailVO::getParentId));

		// 최상위 부모 댓글 리스트 생성
		List<CommentDetailVO> rootComments = comments.stream()
			.filter(comment -> comment.getParentId() == null)
			.toList();

		// 최상위 부모 댓글에서 자식 댓글 조회 
		for (CommentDetailVO rootComment : rootComments) {
			newComments.add(rootComment);
			if (rootComment.getHasChild() == HasChild.Y) {
				addChildrenComments(rootComment, childrenMap, newComments);
			}
		}

		// DTO로 변환하여 반환
		return newComments.stream()
			.map(CommentDetailResDTO::of)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<CommentDetailResDTO> getCommentListUseStack(long postId) {
		// 최신순으로 정렬된 댓글 리스트 가져오기
		List<CommentDetailVO> comments = commentMapper.selectCommentOrderByAsc(postId);

		// 최종 저장되는 댓글 리스트 생성
		List<CommentDetailVO> newComments = new ArrayList<>();

		// 스택 생성
		Deque<CommentDetailVO> stack = new ArrayDeque<>();

		// 댓글 ID, 자식 댓글 리스트를 매핑하는 해시 맵 생성
		Map<Long, List<CommentDetailVO>> childrenMap = new HashMap<>();

		// 최상위 부모 댓글 스택 삽입, 자식 댓글은 해시 맵에 삽입
		for (CommentDetailVO comment : comments) {
			if (comment.getParentId() == null) {
				stack.push(comment);
			} else {
				childrenMap.computeIfAbsent(comment.getParentId(), k -> new ArrayList<>()).add(comment);
			}
		}

		while (!stack.isEmpty()) {
			CommentDetailVO comment = stack.pop();
			newComments.add(comment);

			// 자식 댓글이 존재하지 않으면 continue
			if (comment.getHasChild() == HasChild.N) {
				continue;
			}

			// 현재 댓글의 자식 댓글 리스트 조회
			List<CommentDetailVO> children = childrenMap.get(comment.getCommentId());

			// 자식 댓글들 스택에 삽입
			if (children != null && !children.isEmpty()) {
				for (CommentDetailVO childComment : children) {
					stack.push(childComment);
				}
			}
		}

		// DTO로 변환하여 반환
		return newComments.stream()
			.map(CommentDetailResDTO::of)
			.toList();
	}

	/**
	 * 현재 댓글 추가 및 자식 댓글을 재귀적으로 조회하여 추가
	 *
	 * @param currentComment 현재 댓글 객체
	 * @param childrenMap 자식 댓글 해시 맵
	 * @param newComments 최종 저장되는 댓글 리스트
	 */
	private void addChildrenComments(CommentDetailVO currentComment, Map<Long, List<CommentDetailVO>> childrenMap, List<CommentDetailVO> newComments) {
		// 현재 댓글의 자식 댓글 리스트
		List<CommentDetailVO> children = childrenMap.get(currentComment.getCommentId());

		for (CommentDetailVO childComment : children) {
			newComments.add(childComment);
			if (childComment.getHasChild() == HasChild.Y) {
				addChildrenComments(childComment, childrenMap, newComments);
			}
		}
	}
}
