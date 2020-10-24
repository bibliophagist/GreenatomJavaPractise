package org.example.third.tasks.part.two.service;

import org.example.third.tasks.part.two.model.Comment;
import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment, User user) {
        comment.setAuthor(user);
        commentRepository.save(comment);
        return comment;
    }
}
