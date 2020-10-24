package org.example.third.tasks.part.two.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.third.tasks.part.two.model.Comment;
import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment createComment(
            @RequestBody Comment comment,
            @AuthenticationPrincipal User user) {
        return commentService.createComment(comment, user);
    }
}
