package org.example.third.tasks.controller;

import org.example.third.tasks.model.Role;
import org.example.third.tasks.model.User;
import org.example.third.tasks.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    //TODO not working @PathVariable User user
    //TODO how is this working ?
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{userId}")
    public String userEditForm(@PathVariable Long userId, Model model) {
        Optional<User> user = userService.findById(userId);
        model.addAttribute("user", user.get());
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam Long userId,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username) {
        User user = userService.findById(userId).get();
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
}
