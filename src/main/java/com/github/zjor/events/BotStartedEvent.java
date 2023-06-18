package com.github.zjor.events;

import org.telegram.telegrambots.meta.api.objects.Chat;

public record BotStartedEvent(Long telegramId,
                              String username,
                              String firstName,
                              String lastName) {
    public static BotStartedEvent of(Chat chat) {
        return new BotStartedEvent(chat.getId(), chat.getUserName(), chat.getFirstName(), chat.getLastName());
    }
}
