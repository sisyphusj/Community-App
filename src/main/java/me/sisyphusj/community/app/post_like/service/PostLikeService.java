package me.sisyphusj.community.app.post_like.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.commons.exception.PostNotFoundException;
import me.sisyphusj.community.app.post.mapper.PostMapper;
import me.sisyphusj.community.app.post_like.mapper.PostLikeMapper;
import me.sisyphusj.community.app.utils.SecurityUtil;

@Service
@RequiredArgsConstructor
public class PostLikeService {

	private final PostLikeMapper postLikeMapper;

	private final PostMapper postMapper;

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
		postLikeMapper.insertLikePost(SecurityUtil.getLoginUserId(), postId);

		// 게시글의 좋아요 최신화
		return postLikeMapper.selectLikePost(postId);
	}

	/**
	 * 게시글에 좋아요 취소
	 */
	@Transactional
	public int disLikePost(long postId) {
		// 사용자가 게시글에 좋아요를 남겼는지 확인
		if (postLikeMapper.selectLikePostByUserId(SecurityUtil.getLoginUserId(), postId) == 1) {
			// 게시글에 좋아요 취소 요청
			postLikeMapper.deleteLikePost(SecurityUtil.getLoginUserId(), postId);
		}

		// 게시글의 좋아요 최신화
		return postLikeMapper.selectLikePost(postId);
	}
}
