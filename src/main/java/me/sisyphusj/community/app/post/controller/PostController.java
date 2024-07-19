package me.sisyphusj.community.app.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.commons.LocationUrl;
import me.sisyphusj.community.app.image.service.ImageService;
import me.sisyphusj.community.app.post.domain.*;
import me.sisyphusj.community.app.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static me.sisyphusj.community.app.commons.Constants.*;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final ImageService imageService;

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
    public String showPostPage(@PathVariable long postId, Model model) {
        PostDetailResDTO postDetailResDTO = postService.getPostDetails(postId);

        model.addAttribute("postDetailResDTO", postDetailResDTO);

        // 조회하는 게시글의 첨부 이미지가 존재한다면 이미지 리스트 추가
        if (postDetailResDTO.getHasImage() == HasImage.Y) {
            model.addAttribute("ImageDetailsResDTOList", imageService.getImages(postId));
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
        if (postDetailResDTO.getHasImage() == HasImage.Y) {
            model.addAttribute("ImageDetailsResDTOList", imageService.getImages(postId));
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
