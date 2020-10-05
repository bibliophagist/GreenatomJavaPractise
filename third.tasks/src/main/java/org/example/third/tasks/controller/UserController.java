package org.example.third.tasks.controller;

import org.example.third.tasks.model.Role;
import org.example.third.tasks.model.User;
import org.example.third.tasks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
//TODO how is this working ?
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    //TODO not working @PathVariable User user
    @GetMapping("{userId}")
    public String userEditForm(@PathVariable Long userId, Model model) {
        User user = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(@RequestParam Long userId,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username) {
        User user = userRepository.findById(userId).get();
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoleSet().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoleSet().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }

}
