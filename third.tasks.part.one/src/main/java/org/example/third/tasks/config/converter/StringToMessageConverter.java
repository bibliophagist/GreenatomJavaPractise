package org.example.third.tasks.config.converter;

import org.example.third.tasks.model.Message;
import org.example.third.tasks.repository.MessageRepository;
import org.springframework.core.convert.converter.Converter;

public class StringToMessageConverter implements Converter<String, Message> {
    private final MessageRepository messageRepository;

    public StringToMessageConverter(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //TODO resolve problem with null userId and isPresent check
    @Override
    public Message convert(String userId) {
        return messageRepository.findById(Long.valueOf(userId)).get();
    }
}
