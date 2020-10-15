package org.example.third.tasks.service;

import org.example.third.tasks.model.Message;
import org.example.third.tasks.model.User;
import org.example.third.tasks.model.dto.MessageDto;
import org.example.third.tasks.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Page<MessageDto> messageList(Pageable pageable, String tag, User user) {
        if (tag != null && !tag.isEmpty()) {
            return messageRepository.findByTag(tag, pageable, user);
        } else return messageRepository.findAll(pageable, user);

    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User currentUser, User user) {
        if (currentUser.equals(user)) {
            return messageRepository.findByUser(pageable, user);
        } else {
            return messageRepository.findByUser(pageable, currentUser, user);
        }
    }
}
