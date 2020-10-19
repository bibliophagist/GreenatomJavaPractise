package org.example.third.tasks.part.two.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.third.tasks.part.two.dto.EventType;
import org.example.third.tasks.part.two.dto.ObjectType;
import org.example.third.tasks.part.two.model.Message;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.repository.MessageRepository;
import org.example.third.tasks.part.two.utils.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepository messageRepository;

    private final BiConsumer<EventType, Message> wsSender;

    public MessageController(MessageRepository messageRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
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
        Message messageWithDate = messageRepository.save(message);
        wsSender.accept(EventType.CREATE, messageWithDate);
        return messageWithDate;
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        Message updatedMessage = messageRepository.save(messageFromDb);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepository.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

}
