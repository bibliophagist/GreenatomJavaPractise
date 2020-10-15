package org.example.third.tasks.config.converter;

import org.example.third.tasks.model.User;
import org.example.third.tasks.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class StringToUserConverter implements Converter<String, User> {
    private final UserService userService;

    public StringToUserConverter(UserService userService) {
        this.userService = userService;
    }

    //TODO resolve problem with null userId and isPresent check
    @Override
    public User convert(String userId) {
        return userService.findById(Long.valueOf(userId)).get();
    }
}
