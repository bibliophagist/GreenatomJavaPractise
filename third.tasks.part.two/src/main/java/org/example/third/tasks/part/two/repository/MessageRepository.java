package org.example.third.tasks.part.two.repository;

import org.example.third.tasks.part.two.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
