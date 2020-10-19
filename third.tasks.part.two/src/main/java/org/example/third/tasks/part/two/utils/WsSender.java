package org.example.third.tasks.part.two.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.third.tasks.part.two.dto.EventType;
import org.example.third.tasks.part.two.dto.ObjectType;
import org.example.third.tasks.part.two.dto.WsEventDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class WsSender {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper mapper;

    public WsSender(SimpMessagingTemplate simpMessagingTemplate, ObjectMapper mapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.mapper = mapper;
    }

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class view) {
        ObjectWriter writer = mapper.setConfig(mapper.getSerializationConfig()).writerWithView(view);
        return (EventType eventType, T payload) -> {
            String value = null;
            try {
                value = writer.writeValueAsString(payload);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            simpMessagingTemplate.convertAndSend("/topic/activity",
                    new WsEventDto(objectType, eventType, value));
        };
    }
}
