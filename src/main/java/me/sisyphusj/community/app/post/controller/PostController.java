package me.sisyphusj.community.app.post.controller;

import static me.sisyphusj.community.app.commons.Constants.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.service.CommentService;
import me.sisyphusj.community.app.commons.LocationUrl;
import me.sisyphusj.community.app.image.service.ImageService;
import me.sisyphusj.community.app.post.domain.PageReqDTO;
import me.sisyphusj.community.app.post.domain.PageResDTO;
import me.sisyphusj.community.app.post.domain.PostCreateReqDTO;
import me.sisyphusj.community.app.post.domain.PostDetailResDTO;
import me.sisyphusj.community.app.post.domain.PostEditReqDTO;
import me.sisyphusj.community.app.post.service.PostService;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	private final ImageService imageService;

	private final CommentService commentService;

	/**
	 * 게시판 페이지, 현재 페이지에 맞는 게시글 리스트 반환
	 */
	@GetMapping
	public String showCommunityPage(@Valid @ModelAttribute PageReqDTO pageReqDTO, Model model) {
		PageResDTO pageResDTO = postService.getPostPage(pageReqDTO);

		model.addAttribute("pageResDTO", pageResDTO);
		model.addAttribute("pageReqDTO", pageReqDTO);
		return "community";
	}

	/**
	 * 새로운 게시글 작성 폼 페이지
	 */
	@GetMapping("/new")
	public String showPostPage() {
		return "newPost";
	}

	/**
	 * 게시글 추가
	 */
	@PostMapping("/posts")
	public String createPost(@Valid @ModelAttribute PostCreateReqDTO postCreateReqDTO, Model model) {
		postService.createPost(postCreateReqDTO);

		model.addAttribute(MESSAGE, "게시글이 생성되었습니다.");
		model.addAttribute(LOCATION_URL, LocationUrl.COMMUNITY);
		return MAV_ALERT;
	}

	/**
	 * 게시글 조회
	 */
	@GetMapping("/posts/{postId}")
	public String showPostPage(@PathVariable long postId, @Valid @ModelAttribute PageReqDTO pageReqDTO, Model model) {
		PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId);

		model.addAttribute("postDetailResDTO", postDetailResDTO);

		// 기존 목록 페이지에 대한 정보 유지
		model.addAttribute("pageReqDTO", pageReqDTO);

		// 조회하는 게시글의 첨부 이미지가 존재한다면 이미지 리스트 추가
		if (imageService.hasPostImage(postId)) {
			model.addAttribute("ImageDetailsResDTOList", imageService.getPostImages(postId));
		}

		// 조회하는 게시글의 댓글이 존재한다면 댓글 리스트 추가
		if (commentService.hasComment(postId)) {
			model.addAttribute("commentDetailResDTOList", commentService.getCommentListUseRecursion(postId));
		}

		return "post";
	}

	/**
	 * 게시글 수정 페이지
	 */
	@GetMapping("/posts/{postId}/edit")
	public String showPostEditPage(@PathVariable long postId, Model model) {
		PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId);

		model.addAttribute("postDetailResDTO", postDetailResDTO);

		// 조회하는 게시글의 첨부 이미지가 존재한다면 이미지 리스트 추가
		if (imageService.hasPostImage(postId)) {
			model.addAttribute("ImageDetailsResDTOList", imageService.getPostImages(postId));
		}

		return "editPost";
	}

	/**
	 * 게시글 수정
	 */
	@PostMapping("/posts/edit")
	public String editPost(@Valid @ModelAttribute PostEditReqDTO postEditReqDTO, Model model) {
		postService.editPost(postEditReqDTO);

		model.addAttribute(MESSAGE, "게시글이 수정되었습니다.");
		model.addAttribute(LOCATION_URL, LocationUrl.COMMUNITY);
		return MAV_ALERT;
	}

	/**
	 * 게시글 삭제
	 */
	@GetMapping("/posts/remove")
	public String removePost(@RequestParam long postId, Model model) {
		postService.removePost(postId);

		model.addAttribute(MESSAGE, "게시글이 삭제되었습니다.");
		model.addAttribute(LOCATION_URL, LocationUrl.COMMUNITY);
		return MAV_ALERT;
	}
}
