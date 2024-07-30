package me.sisyphusj.community.app.post.controller;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.List;

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
import me.sisyphusj.community.app.comment.domain.CommentDetailResDTO;
import me.sisyphusj.community.app.comment.service.CommentService;
import me.sisyphusj.community.app.commons.LocationUrl;
import me.sisyphusj.community.app.image.domain.PostImageResDTO;
import me.sisyphusj.community.app.image.service.ImageService;
import me.sisyphusj.community.app.post.domain.BoardType;
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
	public String showCommunityPage(@Valid @ModelAttribute("pageReqDTO") PageReqDTO pageReqDTO, Model model) {
		PageResDTO pageResDTO = postService.getPostPage(pageReqDTO);

		model.addAttribute("pageResDTO", pageResDTO);
		return "community";
	}

	/**
	 * 갤러리 게시판 페이지, 현재 페이지에 맞는 게시글 리스트 반환
	 */
	@GetMapping("/gallery")
	public String showGalleryCommunityPage(@Valid @ModelAttribute("pageReqDTO") PageReqDTO pageReqDTO, Model model) {
		PageResDTO pageResDTO = postService.getImageBoardPage(pageReqDTO);

		model.addAttribute("pageResDTO", pageResDTO);
		return "galleryCommunity";
	}

	/**
	 * 새로운 게시글 작성 폼 페이지
	 */
	@GetMapping("/new")
	public String showPostPage() {
		return "newPost";
	}

	/**
	 * 새로운 이미지 게시글 작성 폼 페이지
	 */
	@GetMapping("gallery/new")
	public String showImageBoardPage() {
		return "newGalleryPost";
	}

	/**
	 * 게시글 추가
	 */
	@PostMapping("/posts")
	public String createPost(@Valid @ModelAttribute PostCreateReqDTO postCreateReqDTO, Model model) {
		postService.createPost(postCreateReqDTO);

		model.addAttribute(MESSAGE, "게시글이 생성되었습니다.");

		// 이미지 게시글 추가 요청이면 이미지 게시판으로 일반 게시글 추가 요청이면 일반 게시판으로 url 설정
		model.addAttribute(LOCATION_URL, postCreateReqDTO.getBoardType() == BoardType.GALLERY ? LocationUrl.GALLERY : LocationUrl.COMMUNITY);

		return MAV_ALERT;
	}

	/**
	 * 게시글 조회
	 */
	@GetMapping("/{boardType}/posts/{postId}")
	public String showPostPage(@PathVariable BoardType boardType, @PathVariable long postId, @Valid @ModelAttribute("pageReqDTO") PageReqDTO pageReqDTO, Model model) {
		PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId, boardType);

		model.addAttribute("postDetailResDTO", postDetailResDTO);

		// 게시글 첨부 이미지, 댓글 추가
		addPostDetails(postId, model);

		// 게시판 타입에 따라 포워딩 페이지 변경
		return boardType == BoardType.GALLERY ? "galleryPost" : "post";
	}

	/**
	 * 게시글 수정 페이지
	 */
	@GetMapping("/{boardType}/posts/{postId}/edit")
	public String showPostEditPage(@PathVariable BoardType boardType, @PathVariable long postId, Model model) {
		PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId, boardType); // 게시글 상세 정보 리스트
		List<PostImageResDTO> imageDetailsResDTOList = imageService.getPostImages(postId); // 이미지 상세 정보 리스트

		model.addAttribute("postDetailResDTO", postDetailResDTO);

		// 조회하는 게시글의 첨부 이미지가 존재한다면 이미지 리스트 추가
		if (!imageDetailsResDTOList.isEmpty()) {
			model.addAttribute("ImageDetailsResDTOList", imageDetailsResDTOList);
		}

		// 게시판 타입에 따라 포워딩 페이지 변경
		return boardType == BoardType.GALLERY ? "editGalleryPost" : "editPost";
	}

	/**
	 * 게시글 수정
	 */
	@PostMapping("/posts/edit")
	public String editPost(@Valid @ModelAttribute PostEditReqDTO postEditReqDTO, Model model) {
		postService.editPost(postEditReqDTO);

		// 알림 메시지 설정, 게시판 타입에 따른 포워딩 페이지 설정
		return setModelAndView(postEditReqDTO.getBoardType(), "게시글이 수정되었습니다.", model);
	}

	/**
	 * 게시글 삭제
	 */
	@GetMapping("/{boardType}/posts/remove")
	public String removePost(@PathVariable BoardType boardType, @RequestParam long postId, Model model) {
		postService.removePost(postId);

		// 알림 메시지 설정, 게시판 타입에 따른 포워딩 페이지 설정
		return setModelAndView(boardType, "게시글이 삭제되었습니다.", model);
	}

	/**
	 * 게시글 첨부 이미지, 댓글 추가
	 */
	private void addPostDetails(long postId, Model model) {
		List<PostImageResDTO> imageDetailsResDTOList = imageService.getPostImages(postId);
		List<CommentDetailResDTO> commentDetailResDTOList = commentService.getCommentListUseRecursion(postId);

		// 조회하는 게시글의 첨부 이미지가 존재한다면 이미지 리스트 추가
		if (!imageDetailsResDTOList.isEmpty()) {
			model.addAttribute("ImageDetailsResDTOList", imageDetailsResDTOList);
		}

		// 조회하는 게시글의 댓글이 존재한다면 댓글 리스트 추가
		if (!commentDetailResDTOList.isEmpty()) {
			model.addAttribute("commentDetailResDTOList", commentDetailResDTOList);
		}
	}

	/**
	 * model 에 알림 메시지 설정, 게시판 타입에 따른 포워딩 페이지 설정
	 *
	 * @param boardType 게시판 타입
	 * @param message 사용자 알림 메시지
	 * @return 사용자 알림 페이지
	 */
	private String setModelAndView(BoardType boardType, String message, Model model) {
		model.addAttribute(MESSAGE, message);
		model.addAttribute(LOCATION_URL, boardType == BoardType.GALLERY ? LocationUrl.GALLERY : LocationUrl.COMMUNITY);
		return MAV_ALERT;
	}
}
