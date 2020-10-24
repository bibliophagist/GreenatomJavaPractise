package org.example.third.tasks.part.two.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.third.tasks.part.two.dto.EventType;
import org.example.third.tasks.part.two.dto.MetaDto;
import org.example.third.tasks.part.two.dto.ObjectType;
import org.example.third.tasks.part.two.model.Message;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.repository.MessageRepository;
import org.example.third.tasks.part.two.utils.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("message")
public class MessageController {
    public static final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    public static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);
    private final MessageRepository messageRepository;

    private final BiConsumer<EventType, Message> wsSender;

    public MessageController(MessageRepository messageRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> listMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("{id}")
    public Message listMessage(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message addMessage(@RequestBody Message message) throws IOException {
        message.setCreationDate(LocalDateTime.now());
        fillMetaDto(message);
        Message messageWithDate = messageRepository.save(message);
        wsSender.accept(EventType.CREATE, messageWithDate);
        return messageWithDate;
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) throws IOException {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        fillMetaDto(messageFromDb);
        Message updatedMessage = messageRepository.save(messageFromDb);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepository.delete(message);
        wsSender.accept(EventType.REMOVE, message);
    }

    private void fillMetaDto(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());
            message.setLink(url);
            matcher = IMAGE_REGEX.matcher(url);
            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto metaDto = getMetaDto(url);
                message.setLinkCover(metaDto.getCover());
                message.setLinkTitle(metaDto.getTitle());
                message.setLinkDescription(metaDto.getDescription());
            }
        }
    }

    private MetaDto getMetaDto(String url) throws IOException {
        Document document = Jsoup.connect(url).ignoreContentType(true).get();
        Elements title = document.select("meta[name$=title],meta[property$=title]");
        Elements description = document.select("meta[name$=description],meta[property$=description]");
        Elements cover = document.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(getContent(title.first()), getContent(description.first()), getContent(cover.first()));
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

}
