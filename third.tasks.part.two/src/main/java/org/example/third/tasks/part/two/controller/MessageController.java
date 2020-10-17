package org.example.third.tasks.part.two.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.third.tasks.part.two.model.Message;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.repository.MessageRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> listMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("{id}")
    public Message listMessage(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepository.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepository.delete(message);
    }
}
