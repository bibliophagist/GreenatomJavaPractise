package org.example.third.tasks.part.two.service;

import org.example.third.tasks.part.two.dto.EventType;
import org.example.third.tasks.part.two.dto.ObjectType;
import org.example.third.tasks.part.two.model.Comment;
import org.example.third.tasks.part.two.model.Message;
import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.repository.CommentRepository;
import org.example.third.tasks.part.two.utils.WsSender;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    public CommentService(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment createComment(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentRepository.save(comment);
        wsSender.accept(EventType.CREATE, commentFromDb);
        return commentFromDb;
    }
}
