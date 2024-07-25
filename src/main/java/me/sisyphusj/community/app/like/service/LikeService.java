package me.sisyphusj.community.app.like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.mapper.CommentMapper;
import me.sisyphusj.community.app.commons.exception.PostNotFoundException;
import me.sisyphusj.community.app.like.mapper.LikeMapper;
import me.sisyphusj.community.app.post.mapper.PostMapper;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeMapper likeMapper;

	private final PostMapper postMapper;

	private final CommentMapper commentMapper;

	/**
	 * 게시글에 좋아요 추가
	 */
	@Transactional
	public int addLikePost(long postId) {
		// 게시글에 대한 유효성 확인
		if (postMapper.selectCountPost(postId) != 1) {
			throw new PostNotFoundException();
		}

		// 게시글에 좋아요 추가 요청
		likeMapper.insertLikePost(SecurityUtil.getLoginUserId(), postId);

		// 게시글의 좋아요 최신화
		return likeMapper.selectLikePost(postId);
	}

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
		likeMapper.insertLikeComment(SecurityUtil.getLoginUserId(), commentId);

		// 댓글의 좋아요 최신화
		return likeMapper.selectLikeComment(commentId);
	}

	/**
	 * 게시글에 대한 좋아요 개수 조회
	 */
	@Transactional(readOnly = true)
	public int countLikePost(long postId) {
		return likeMapper.selectLikePost(postId);
	}

	/**
	 * 댓글에 대한 좋아요 개수 조회
	 */
	@Transactional(readOnly = true)
	public int countLikeComment(long commentId) {
		return likeMapper.selectLikeComment(commentId);
	}

	/**
	 * 사용자가 게시글에 좋아요를 남겼는지 확인
	 *
	 * @return 사용자가 게시글에 좋아요를 남겼으면 true, 남기지 않았으면 false 반환
	 */
	@Transactional(readOnly = true)
	public boolean hasPostLike(long postId) {
		return likeMapper.selectLikePostByUserId(SecurityUtil.getLoginUserId(), postId) == 1;
	}

	/**
	 * 사용자가 댓글에 좋아요를 남겼는지 확인
	 *
	 * @return 사용자가 댓글에 좋아요를 남겼으면 true, 남기지 않았으면 false 반환
	 */
	@Transactional(readOnly = true)
	public boolean hasCommentLike(long commentId) {
		return likeMapper.selectLikeCommentByUserId(SecurityUtil.getLoginUserId(), commentId) == 1;
	}

	/**
	 * 게시글에 좋아요 취소
	 */
	@Transactional
	public int disLikePost(long postId) {
		// 사용자가 게시글에 좋아요를 남겼는지 확인
		if (likeMapper.selectLikePostByUserId(SecurityUtil.getLoginUserId(), postId) == 1) {
			// 게시글에 좋아요 취소 요청
			likeMapper.deleteLikePost(SecurityUtil.getLoginUserId(), postId);
		}

		// 게시글의 좋아요 최신화
		return likeMapper.selectLikePost(postId);
	}

	/**
	 * 댓글에 좋아요 취소
	 */
	@Transactional
	public int disLikeComment(long commentId) {
		// 사용자가 댓글에 좋아요를 남겼는지 확인
		if (likeMapper.selectLikeCommentByUserId(SecurityUtil.getLoginUserId(), commentId) == 1) {
			// 댓글에 좋아요 취소 요청
			likeMapper.deleteLikeComment(SecurityUtil.getLoginUserId(), commentId);
		}

		// 댓글의 좋아요 최신화
		return likeMapper.selectLikeComment(commentId);
	}
}
