package org.example.third.tasks.part.two.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.third.tasks.part.two.model.Message;
import org.example.third.tasks.part.two.model.Views;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    private List<Message> messages;
    private int currentPage;
    private int totalPages;
}
