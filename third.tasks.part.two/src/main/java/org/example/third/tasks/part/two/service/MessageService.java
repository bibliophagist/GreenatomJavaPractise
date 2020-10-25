package org.example.third.tasks.part.two.service;

import org.example.third.tasks.part.two.dto.EventType;
import org.example.third.tasks.part.two.dto.MessagePageDto;
import org.example.third.tasks.part.two.dto.MetaDto;
import org.example.third.tasks.part.two.dto.ObjectType;
import org.example.third.tasks.part.two.model.Message;
import org.example.third.tasks.part.two.model.User;
import org.example.third.tasks.part.two.model.UserSubscription;
import org.example.third.tasks.part.two.model.Views;
import org.example.third.tasks.part.two.repository.MessageRepository;
import org.example.third.tasks.part.two.repository.UserSubscriptionRepository;
import org.example.third.tasks.part.two.utils.WsSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageService {

    public static final String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    public static final String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);
    private final MessageRepository messageRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    private final BiConsumer<EventType, Message> wsSender;

    public MessageService(MessageRepository messageRepository, UserSubscriptionRepository userSubscriptionRepository, WsSender wsSender) {
        this.messageRepository = messageRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.wsSender = wsSender.getSender(ObjectType.MESSAGE, Views.IdName.class);
    }

    public Message update(Message messageFromDb, Message message) throws IOException {
        BeanUtils.copyProperties(message, messageFromDb, "id", "comments", "author");
        fillMetaDto(messageFromDb);
        Message updatedMessage = messageRepository.save(messageFromDb);
        wsSender.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    public Message addMessage(Message message, User user) throws IOException {
        message.setCreationDate(LocalDateTime.now());
        fillMetaDto(message);
        message.setAuthor(user);
        Message messageWithDate = messageRepository.save(message);
        wsSender.accept(EventType.CREATE, messageWithDate);
        return messageWithDate;
    }

    public void delete(Message message) {
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


    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepository.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepository.findByAuthorIn(channels, pageable);
        return new MessagePageDto(page.getContent(), pageable.getPageNumber(), page.getTotalPages());
    }
}
