package me.sisyphusj.community.app.comment.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.domain.CommentDetailResDTO;
import me.sisyphusj.community.app.comment.domain.CommentEditReqDTO;
import me.sisyphusj.community.app.comment.domain.CommentReqDTO;
import me.sisyphusj.community.app.comment.domain.CommentVO;
import me.sisyphusj.community.app.comment.mapper.CommentMapper;
import me.sisyphusj.community.app.commons.exception.CommentNotFoundException;
import me.sisyphusj.community.app.utils.SecurityUtil;

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
		List<CommentVO> comments = commentMapper.selectCommentList(postId);

		// 최종 저장되는 댓글 리스트 생성
		List<CommentVO> newComments = new ArrayList<>();

		// 댓글 ID, 자식 댓글 리스트를 매핑하는 해시 맵 생성
		Map<Long, List<CommentVO>> childrenMap = createChildrenMap(comments);

		// 최상위 부모 댓글 리스트 생성
		List<CommentVO> rootComments = comments.stream()
			.filter(comment -> comment.getParentId() == null)
			.toList();

		// 최상위 부모 댓글에서 자식 댓글 조회
		for (CommentVO rootComment : rootComments) {
			newComments.add(rootComment);
			addChildrenComments(rootComment, childrenMap, newComments);
		}

		// DTO로 변환하여 반환
		return newComments.stream()
			.map(CommentDetailResDTO::of)
			.toList();
	}

	/**
	 * 댓글 수정
	 */
	@Transactional
	public void editComment(CommentEditReqDTO commentEditReqDTO) {
		// 작성자와 수정하려는 댓글이 존재하는지 확인
		if (commentMapper.selectComment(SecurityUtil.getLoginUserId(), commentEditReqDTO.getCommentId()).isEmpty()) {
			throw new CommentNotFoundException();
		}

		commentMapper.editComment(CommentVO.of(commentEditReqDTO));
	}

	/**
	 * 댓글 삭제
	 */
	@Transactional
	public void removeComment(long postId, long commentId) {
		// 삭제 대상 댓글 id 리스트
		List<Long> deleteCommentIdList = new ArrayList<>();

		// 최신순으로 정렬된 댓글 리스트 가져오기
		List<CommentVO> comments = commentMapper.selectCommentList(postId);

		// 작성자 확인 및 삭제하려는 댓글이 존재하는지 확인
		boolean isCommentExist = comments.stream()
			.anyMatch(comment -> comment.getCommentId() == commentId && comment.getUserId() == SecurityUtil.getLoginUserId());

		if (!isCommentExist) {
			throw new CommentNotFoundException();
		}

		// 해당 댓글의 자식 댓글이 존재한다면 자식 댓글도 삭제
		collectDeletableComments(commentId, comments, deleteCommentIdList);

		// 작성자의 id 및 댓글 id 리스트를 통해 삭제 요청
		commentMapper.deleteComment(SecurityUtil.getLoginUserId(), deleteCommentIdList);
	}

	/**
	 * 댓글 리스트 조회 (스택)
	 */
	@Transactional(readOnly = true)
	public List<CommentDetailResDTO> getCommentListUseStack(long postId) {
		// 댓글 리스트 가져오기
		List<CommentVO> comments = commentMapper.selectCommentListOrderByAsc(postId);

		// 최종 저장되는 댓글 리스트 생성
		List<CommentVO> newComments = new ArrayList<>();

		// 스택 생성
		Deque<CommentVO> stack = new ArrayDeque<>();

		// 댓글 ID, 자식 댓글 리스트를 매핑하는 해시 맵 생성
		Map<Long, List<CommentVO>> childrenMap = createChildrenMap(comments);

		// 최상위 부모 댓글 스택 삽입, 자식 댓글은 해시 맵에 삽입
		for (CommentVO comment : comments) {
			if (comment.getParentId() == null) {
				stack.push(comment);
			}
		}

		while (!stack.isEmpty()) {
			CommentVO comment = stack.pop();
			newComments.add(comment);

			// 현재 댓글의 자식 댓글 리스트 조회
			List<CommentVO> children = childrenMap.get(comment.getCommentId());

			// 자식 댓글들 스택에 삽입
			if (children != null && !children.isEmpty()) {
				for (CommentVO childComment : children) {
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
	 * 전체 댓글 리스트에서 댓글 ID, 자식 댓글 리스트를 매핑하는 해시 맵 반환
	 */
	private Map<Long, List<CommentVO>> createChildrenMap(List<CommentVO> comments) {
		return comments.stream()
			.filter(comment -> comment.getParentId() != null)
			.collect(Collectors.groupingBy(CommentVO::getParentId));
	}

	/**
	 * 현재 댓글 추가 및 자식 댓글을 재귀적으로 조회하여 추가
	 *
	 * @param currentComment 현재 댓글 객체
	 * @param childrenMap 자식 댓글 해시 맵
	 * @param newComments 최종 저장되는 댓글 리스트
	 */
	private void addChildrenComments(CommentVO currentComment, Map<Long, List<CommentVO>> childrenMap, List<CommentVO> newComments) {
		// 현재 댓글의 자식 댓글 리스트
		List<CommentVO> children = childrenMap.get(currentComment.getCommentId());

		if (children != null && !children.isEmpty()) {
			for (CommentVO childComment : children) {
				newComments.add(childComment);
				addChildrenComments(childComment, childrenMap, newComments);
			}
		}
	}

	/**
	 * 재귀적으로 자식 댓글의 ID를 조회하여 자신의 ID와 함께 deleteCommentIdList 에 저장
	 */
	private void collectDeletableComments(long commentId, List<CommentVO> comments, List<Long> deleteCommentIdList) {
		deleteCommentIdList.add(commentId);

		// 자식 댓글 리스트 조회 후 각 댓글을 통해 메소드 호출
		comments.stream()
			.filter(comment -> Objects.equals(comment.getParentId(), commentId))
			.forEach(comment -> collectDeletableComments(comment.getCommentId(), comments, deleteCommentIdList));
	}
}
