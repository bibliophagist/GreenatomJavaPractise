package org.example.third.tasks.part.two.repository;

import org.example.third.tasks.part.two.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @EntityGraph(attributePaths = {"comments"})
    Page<Message> findAll(Pageable pageable);
}
