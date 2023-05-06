package com.github.zjor.bot.commands;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class BotCommand {

    protected final DefaultAbsSender sender;
    protected final Long chatId;

    public BotCommand(DefaultAbsSender sender, Long chatId) {
        this.sender = sender;
        this.chatId = chatId;
    }

    abstract public BotCommand start();

    public void cancel() {
        throw new UnsupportedOperationException();
    }

    public void text(String text) {
        throw new UnsupportedOperationException();
    }

    public boolean isFinished() {
        return true;
    }

    @SneakyThrows
    protected void reply(String text) {
        sender.execute(SendMessage.builder().chatId(chatId).text(text).build());
    }

}
