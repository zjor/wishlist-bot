package com.github.zjor.bot.commands;

import com.github.zjor.domain.User;
import com.github.zjor.repository.WishlistItemRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.stream.Collectors;

@Slf4j
public class ViewItemCommand extends BotCommand {

    private final String initialText;
    private final User user;
    private final WishlistItemRepository wishlistItemRepository;

    private String itemId;

    private boolean finished = false;

    public ViewItemCommand(
            DefaultAbsSender sender,
            Long chatId,
            String initialText,
            User user,
            WishlistItemRepository wishlistItemRepository) {
        super(sender, chatId);
        this.initialText = initialText;
        this.user = user;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    public BotCommand start() {
        String[] tokens = initialText.split("\\s+");
        if (tokens.length > 1) {
            sendItem(tokens[1]);
        } else {
            reply("Send an item ID");
        }
        return this;
    }

    @Override
    public void cancel() {
        finished = true;
        reply("Got it");
    }

    @Override
    public void text(String text) {
        if (finished) {
            reply("I shouldn't be in that state");
        } else {
            sendItem(text);
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @SneakyThrows
    private void sendItem(String itemId) {
        finished = true;
        var itemOpt = wishlistItemRepository.findById(itemId);
        if (itemOpt.isEmpty()) {
            reply("Item with ID: '" + itemId + "' was not found");
        } else {
            var item = itemOpt.get();

            var sb = new StringBuilder()
                    .append('*').append(item.getName()).append('*').append("\n\n")
                    .append("```\n").append(item.getDescription()).append("\n```\n")
                    .append(item.getTags().stream().map(it -> '#' + it).collect(Collectors.joining(", ")))
                    .append('\n');

            sender.execute(SendMessage.builder()
                    .chatId(chatId)
                    .parseMode(ParseMode.MARKDOWN)
                    .text(sb.toString())
                    .build());
        }
    }
}
