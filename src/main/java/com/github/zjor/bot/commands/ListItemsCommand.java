package com.github.zjor.bot.commands;

import com.github.zjor.domain.User;
import com.github.zjor.repository.WishlistItemRepository;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.List;

public class ListItemsCommand extends BotCommand {

    private final User user;
    private final WishlistItemRepository wishlistItemRepository;

    public ListItemsCommand(
            DefaultAbsSender sender,
            Long chatId,
            User user,
            WishlistItemRepository wishlistItemRepository) {
        super(sender, chatId);
        this.user = user;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @SneakyThrows
    @Override
    public BotCommand start() {
        var items = wishlistItemRepository.findByOwner(user);
        var sb = new StringBuilder();

        items.forEach(item ->
                sb.append(String.format("- `%s` *%s* %s\n",
                        item.getId(),
                        item.getName(),
                        trim(item.getDescription(), 64, "...")))
        );
        if (sb.isEmpty()) {
            sb.append("No items yet, please type `/create` to add");
        }
        sender.execute(SendMessage.builder()
                .chatId(chatId)
                .text(sb.append('\n').toString())
                .parseMode(ParseMode.MARKDOWN)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(List.of(
                                InlineKeyboardButton.builder()
                                        .text("Open the app")
                                        .webApp(WebAppInfo.builder()
                                                .url("https://twa-wishlist-bot.surge.sh")
                                                .build())
                                        .build()
                        ))
                        .build())
                .build());
        return this;
    }

    private String trim(String value, int maxLength, String ellipsis) {
        if (value != null && value.length() > maxLength) {
            return value.substring(0, maxLength - ellipsis.length() - 1) + ellipsis;
        } else {
            return value;
        }
    }
}
