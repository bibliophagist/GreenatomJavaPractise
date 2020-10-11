package org.example.third.tasks.controller;

import org.example.third.tasks.model.Message;
import org.example.third.tasks.model.User;
import org.example.third.tasks.repository.MessageRepository;
import org.example.third.tasks.utils.ControllerUtils;
import org.example.third.tasks.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

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
    public String add(@AuthenticationPrincipal User user,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            MessageUtils.saveFile(message, file);
            model.addAttribute("message", null);
            messageRepository.save(message);
        }
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }

}

