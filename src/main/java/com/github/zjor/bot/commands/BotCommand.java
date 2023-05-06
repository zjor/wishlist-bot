package com.github.zjor.bot.commands;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class BotCommand {

    private final DefaultAbsSender sender;
    private final Long chatId;

    public BotCommand(DefaultAbsSender sender, Long chatId) {
        this.sender = sender;
        this.chatId = chatId;
    }

    abstract public void start();

    abstract public void cancel();

    abstract public void text(String text);

    abstract public boolean isFinished();

    @SneakyThrows
    protected void reply(String text) {
        sender.execute(SendMessage.builder().chatId(chatId).text(text).build());
    }

}
