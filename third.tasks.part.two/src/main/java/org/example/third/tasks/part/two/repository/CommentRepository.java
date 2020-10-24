package org.example.third.tasks.part.two.repository;

import org.example.third.tasks.part.two.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
