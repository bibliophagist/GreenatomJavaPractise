package org.example.third.tasks.controller;

import org.example.third.tasks.model.Role;
import org.example.third.tasks.model.User;
import org.example.third.tasks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Map<String, Object> model, User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User already exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoleSet(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
