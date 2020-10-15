package org.example.third.tasks.model.dto;

import org.example.third.tasks.model.Message;
import org.example.third.tasks.model.User;
import org.example.third.tasks.utils.MessageHelper;

public class MessageDto {
    private final Long id;
    private final String text;
    private final String tag;
    private final String filename;
    private final Long likes;
    private final User author;
    private final boolean meLiked;

    public MessageDto(Message message, Long likes, boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.filename = message.getFilename();
        this.author = message.getAuthor();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return MessageHelper.getAuthorName(author);
    }

    public boolean isMeLiked() {
        return meLiked;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public String getFilename() {
        return filename;
    }

    public Long getLikes() {
        return likes;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", likes=" + likes +
                ", author=" + author +
                ", meLiked=" + meLiked +
                '}';
    }
}
