package me.sisyphusj.community.app.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sisyphusj.community.app.comment.domain.CommentReqDTO;
import me.sisyphusj.community.app.comment.service.CommentService;
import me.sisyphusj.community.app.commons.LocationUrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static me.sisyphusj.community.app.commons.Constants.*;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     */
    @PostMapping
    public String createComment(@Valid @ModelAttribute CommentReqDTO commentReqDTO, Model model) {
        commentService.createComment(commentReqDTO);
        model.addAttribute(MESSAGE, "댓글이 등록되었습니다.");
        model.addAttribute(LOCATION_URL, LocationUrl.CUSTOM);
        model.addAttribute(CUSTOM_URL, "/community/posts/" + commentReqDTO.getPostId());
        return MAV_ALERT;
    }
}
