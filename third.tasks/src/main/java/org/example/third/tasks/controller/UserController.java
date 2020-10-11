package org.example.third.tasks.controller;

import org.example.third.tasks.model.Message;
import org.example.third.tasks.model.Role;
import org.example.third.tasks.model.User;
import org.example.third.tasks.repository.MessageRepository;
import org.example.third.tasks.service.UserService;
import org.example.third.tasks.utils.ControllerUtils;
import org.example.third.tasks.utils.MessageUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")

public class UserController {
    private final UserService userService;
    private final MessageRepository messageRepository;

    public UserController(UserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    //TODO how is this working ?
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam User user,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email) {
        userService.updateProfile(user, password, email);
        return "redirect:/user/profile";
    }

    @GetMapping("/messages/{user}")
    public String userMessages(@AuthenticationPrincipal User currentUser,
                               @PathVariable User user,
                               Model model,
                               @RequestParam(required = false) Message message) {
        Set<Message> messages = user.getMessages();

        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }

    @PostMapping("/messages/{user}")
    public String updateMessage(@AuthenticationPrincipal User currentUser,
                                @PathVariable Long user,
                                Model model,
                                @RequestParam("id") Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file") MultipartFile file) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }
            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            MessageUtils.saveFile(message, file);
            messageRepository.save(message);
        }
        return "redirect:/user/messages/" + user;

    }
}
