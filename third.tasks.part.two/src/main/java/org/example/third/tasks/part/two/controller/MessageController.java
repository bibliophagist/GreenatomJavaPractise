package org.example.third.tasks.part.two.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.third.tasks.part.two.dto.MessagePageDto;
import org.example.third.tasks.part.two.model.Message;
import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.service.MessageService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("message")
public class MessageController {

    public static final int MESSAGES_PER_PAGE = 3;

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping
    @JsonView(Views.FullMessage.class)
    public MessagePageDto listMessages(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = MESSAGES_PER_PAGE, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return messageService.findForUser(pageable, user);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullMessage.class)
    public Message listMessage(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message, @AuthenticationPrincipal User user) throws IOException {
        return messageService.addMessage(message, user);
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) throws IOException {

        return messageService.update(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageService.delete(message);
    }


}
