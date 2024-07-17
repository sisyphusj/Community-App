package me.sisyphusj.community.app.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.post.domain.PageResDTO;
import me.sisyphusj.community.app.post.domain.PageSortType;
import me.sisyphusj.community.app.post.domain.PostCreateReqDTO;
import me.sisyphusj.community.app.post.domain.PostDetailResDTO;
import me.sisyphusj.community.app.post.domain.PostEditReqDTO;
import me.sisyphusj.community.app.post.service.PostService;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	/**
	 * 게시판 페이지, 현재 페이지에 맞는 게시글 리스트 반환
	 */
	@GetMapping
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
	@PostMapping("/posts")
	public ResponseEntity<HttpStatus> createPost(@Valid @ModelAttribute PostCreateReqDTO postCreateReqDTO) {
		postService.createPost(postCreateReqDTO);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	/**
	 * 게시글 조회
	 */
	@GetMapping("/posts/{postId}")
	public String showPostPage(@PathVariable long postId, Model model) {
		PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId);
		model.addAttribute("postDetailResDTO", postDetailResDTO);
		return "post";
	}

	/**
	 * 게시글 수정 페이지
	 */
	@GetMapping("/posts/{postId}/edit")
	public String showPostEditPage(@PathVariable long postId, Model model) {
		PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId);
		model.addAttribute("postDetailResDTO", postDetailResDTO);
		return "editPost";
	}

	/**
	 * 게시글 수정
	 */
	@PostMapping("/posts/edit")
	public String editPost(@Valid @ModelAttribute PostEditReqDTO postEditReqDTO) {
		postService.editPost(postEditReqDTO);
		return "community";
	}

	/**
	 * 게시글 삭제
	 */
	@DeleteMapping("/posts/remove")
	public ResponseEntity<Void> removePost(@RequestParam long postId) {
		postService.removePost(postId);
		return ResponseEntity.ok().build();
	}
}
