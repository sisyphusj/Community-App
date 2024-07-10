package me.sisyphusj.community.app.post.controller;

import static me.sisyphusj.community.app.commons.Constants.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.commons.RedirectType;
import me.sisyphusj.community.app.post.domain.CreatePostReqDTO;
import me.sisyphusj.community.app.post.domain.PostThumbnailResDTO;
import me.sisyphusj.community.app.post.service.PostService;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	/**
	 * 게시글 리스트 페이지
	 */
	@GetMapping
	public String showCommunityPage() {
		return "community";
	}

	/**
	 * 게시글 섬네일 리스트 불러오기
	 */
	@GetMapping("/posts")
	public ResponseEntity<List<PostThumbnailResDTO>> getPostThumbnailList(@RequestParam int amount, @RequestParam int offset) {
		return ResponseEntity.ok(postService.getPostThumbnailList(amount, offset));
	}

	/**
	 * 게시글 페이지
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
}
