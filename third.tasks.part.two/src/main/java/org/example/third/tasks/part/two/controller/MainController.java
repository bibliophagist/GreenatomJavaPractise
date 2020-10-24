package org.example.third.tasks.part.two.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepository messageRepository;

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter writer;

    public MainController(MessageRepository messageRepository, ObjectMapper mapper) {
        this.messageRepository = messageRepository;
        this.writer = mapper.setConfig(mapper.getSerializationConfig()).writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        if (user != null) {
            data.put("profile", user);
            model.addAttribute("messages", writer.writeValueAsString(messageRepository.findAll()));
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
