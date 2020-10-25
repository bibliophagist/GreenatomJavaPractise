package org.example.third.tasks.part.two.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.third.tasks.part.two.dto.MessagePageDto;
import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.repository.UserDetailsRepository;
import org.example.third.tasks.part.two.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageService messageService;
    private final UserDetailsRepository userDetailsRepository;

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;

    public MainController(MessageService messageService, UserDetailsRepository userDetailsRepository, ObjectMapper mapper) {
        this.messageService = messageService;
        this.userDetailsRepository = userDetailsRepository;
        ObjectMapper objectMapper = mapper.setConfig(mapper.getSerializationConfig());
        this.messageWriter = objectMapper.writerWithView(Views.FullMessage.class);
        this.profileWriter = objectMapper.writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        if (user != null) {
            User userFromDb = userDetailsRepository.findById(user.getId()).get();
            model.addAttribute("profile", profileWriter.writeValueAsString(userFromDb));

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, sort);
            MessagePageDto messagePageDto = messageService.findForUser(pageRequest, user);

            model.addAttribute("messages", messageWriter.writeValueAsString(messagePageDto.getMessages()));
            data.put("currentPage", messagePageDto.getCurrentPage());
            data.put("totalPages", messagePageDto.getTotalPages());
        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index";
    }
}
