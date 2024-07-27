package me.sisyphusj.community.app.comment_like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.mapper.CommentMapper;
import me.sisyphusj.community.app.comment_like.mapper.CommentLikeMapper;
import me.sisyphusj.community.app.commons.exception.PostNotFoundException;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

	private final CommentLikeMapper commentLikeMapper;

	private final CommentMapper commentMapper;

	/**
	 * 댓글에 좋아요 추가
	 */
	@Transactional
	public int addLikeComment(long commentId) {
		// 댓글에 대한 유효성 확인
		if (commentMapper.selectCountCommentByCommentId(commentId) != 1) {
			throw new PostNotFoundException();
		}

		// 댓글에 좋아요 추가 요청
		commentLikeMapper.insertLikeComment(SecurityUtil.getLoginUserId(), commentId);

		// 댓글의 좋아요 최신화
		return commentLikeMapper.selectLikeComment(commentId);
	}

	/**
	 * 댓글에 좋아요 취소
	 */
	@Transactional
	public int disLikeComment(long commentId) {
		// 사용자가 댓글에 좋아요를 남겼는지 확인
		if (commentLikeMapper.selectLikeCommentByUserId(SecurityUtil.getLoginUserId(), commentId) == 1) {
			// 댓글에 좋아요 취소 요청
			commentLikeMapper.deleteLikeComment(SecurityUtil.getLoginUserId(), commentId);
		}

		// 댓글의 좋아요 최신화
		return commentLikeMapper.selectLikeComment(commentId);
	}
}
