package com.github.zjor;

import com.github.zjor.repository.UserRepository;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class WishListBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    public WishListBot(String botToken, UserRepository userRepository) {
        super(botToken);
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long userId = message.getChatId();
            var text = message.getText();

            userRepository.ensure((int) userId, message.getChat().getUserName());

            if (text.startsWith("/start")) {
                handleStart(message);
            } else {
                handleOtherMessage(message);
            }
        }
    }

    @SneakyThrows
    private void handleStart(Message message) {
        execute(SendMessage.builder()
                .chatId(message.getChatId())
                .text(String.format("Hello %s!\nWelcome to the WishListBot", message.getChat().getFirstName()))
                .build());
    }

    @SneakyThrows
    private void handleOtherMessage(Message message) {
        var userId = message.getChatId();
        SendMessage command = SendMessage.builder()
                .chatId(userId)
                .text("`" + message.getText() + "`")
                .parseMode(ParseMode.MARKDOWN)
                .replyMarkup(new InlineKeyboardMarkup(
                        List.of(
                                List.of(
                                        InlineKeyboardButton.builder()
                                                .text("Click Me!")
                                                .url("https://www.google.com")
                                                .build()
                                )
                        )

                ))
                .build();
        execute(command);
    }

    @Override
    public String getBotUsername() {
        return "wishlistBot";
    }
}
