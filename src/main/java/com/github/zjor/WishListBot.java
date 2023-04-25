package com.github.zjor;

import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class WishListBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    private final WishlistItemRepository wishlistItemRepository;

    /**
     * telegram ID -> is creating new item
     */
    private final Map<String, Boolean> userState = new HashMap<>();

    public WishListBot(
            String botToken,
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository) {
        super(botToken);
        this.userRepository = userRepository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            var chat = message.getChat();
            long userId = message.getChatId();
            var text = message.getText();

            log.info("Ensuring user exists: ID {}", userId);
            var user = userRepository.ensure(String.valueOf(chat.getId()), chat.getUserName(), chat.getFirstName(), chat.getLastName());

            if (text.startsWith("/start")) {
                handleStart(message);
            } else if (text.startsWith("/create")) {
                userState.put(String.valueOf(userId), true);
                reply(message, "Enter item name and description separated by comma");
            } else if (userState.getOrDefault(String.valueOf(userId), false)) {
                String[] parts = message.getText().split(",");
                var item = wishlistItemRepository.create(user,
                        parts[0],
                        parts[1], null, null, null);
                reply(message, "New item created. ID: " + item.getId());
                userState.put(String.valueOf(userId), false);
            } else if(text.startsWith("/list")) {
                var items = wishlistItemRepository.findByOwner(user);
                var sb = new StringBuilder();
                items.forEach(item ->
                        sb.append(item.getId())
                                .append(" - ")
                                .append(item.getName())
                                .append(" - ")
                                .append(item.getDescription())
                                .append("\n")
                );
                reply(message, sb.toString());
            } else {
                handleOtherMessage(message);
            }
        }
    }

    @SneakyThrows
    private void reply(Message message, String text) {
        execute(SendMessage.builder().chatId(message.getChatId()).text(text).build());
    }

    @SneakyThrows
    private void handleStart(Message message) {
        reply(message, String.format("Hello %s!\nWelcome to the WishListBot", message.getChat().getFirstName()));
    }

    @SneakyThrows
    private void handleOtherMessage(Message message) {
        var userId = message.getChatId();
        SendMessage command = SendMessage.builder().chatId(userId).text("`" + message.getText() + "`").parseMode(ParseMode.MARKDOWN).replyMarkup(new InlineKeyboardMarkup(List.of(List.of(InlineKeyboardButton.builder().text("Click Me!").url("https://www.google.com").build()))

        )).build();
        execute(command);
    }

    @Override
    public String getBotUsername() {
        return "wishlistBot";
    }
}
