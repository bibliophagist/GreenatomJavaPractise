package org.example.third.tasks.utils;

import org.example.third.tasks.model.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
