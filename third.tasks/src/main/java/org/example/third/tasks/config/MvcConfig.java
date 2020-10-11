package org.example.third.tasks.config;

import org.example.third.tasks.config.converter.StringToMessageConverter;
import org.example.third.tasks.config.converter.StringToUserConverter;
import org.example.third.tasks.repository.MessageRepository;
import org.example.third.tasks.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;
    private final UserService userService;
    private final MessageRepository messageRepository;

    public MvcConfig(UserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToUserConverter(userService));
        registry.addConverter(new StringToMessageConverter(messageRepository));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/" + uploadPath + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
