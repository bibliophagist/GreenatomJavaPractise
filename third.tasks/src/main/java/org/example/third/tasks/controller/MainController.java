package org.example.third.tasks.controller;

import org.example.third.tasks.model.Message;
import org.example.third.tasks.model.User;
import org.example.third.tasks.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greetings";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String tag, Model model) {
        Iterable<Message> messages;

        if (tag != null && tag.isEmpty()) {
            messages = messageRepository.findAll();
        } else messages = messageRepository.findByTag(tag);

        model.addAttribute("messages", messages);
        model.addAttribute("filter", tag);


        return "main";
    }

    @PostMapping("/add")
    public String add(@AuthenticationPrincipal User user, Model model,
                      @RequestParam String text, @RequestParam String tag,
                      @RequestParam("file") MultipartFile file) throws IOException {
        Message message = new Message(text, tag, user);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            message.setFilename(fileName);
        }

        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

}

