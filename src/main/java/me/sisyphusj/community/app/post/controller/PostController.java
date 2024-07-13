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
import lombok.extern.slf4j.Slf4j;
import me.sisyphusj.community.app.commons.RedirectType;
import me.sisyphusj.community.app.post.domain.CreatePostReqDTO;
import me.sisyphusj.community.app.post.domain.PageResDTO;
import me.sisyphusj.community.app.post.domain.PageSortType;
import me.sisyphusj.community.app.post.domain.PostDetailResDTO;
import me.sisyphusj.community.app.post.service.PostService;

@Slf4j
@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	/**
	 * 게시판 페이지, 현재 페이지에 맞는 게시글 리스트 반환
	 */
	@GetMapping()
	public String showCommunityPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "DATE") PageSortType sort, Model model) {
		PageResDTO pageResDTO = postService.getPostPage(page, sort);
		model.addAttribute("pageResDTO", pageResDTO);
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
	@PostMapping("/add")
	public String createPost(@Valid @ModelAttribute CreatePostReqDTO createPostReqDTO, Model model) {
		postService.createdPost(createPostReqDTO);
		model.addAttribute(MESSAGE, "저장되었습니다.");
		model.addAttribute(REDIRECT_URL, RedirectType.COMMUNITY);
		return MAV_ALERT;
	}

	/**
	 * 게시글 조회
	 */
	@GetMapping("/posts/{postId}")
	public String showPostPage(@PathVariable int postId, Model model) {
		PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId);
		model.addAttribute("postDetailResDTO", postDetailResDTO);
		return "post";
	}
}
