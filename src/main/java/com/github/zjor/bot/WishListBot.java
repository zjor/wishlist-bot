package com.github.zjor.bot;

import com.github.zjor.bot.commands.BotCommand;
import com.github.zjor.bot.commands.CreateWishlistItemCommand;
import com.github.zjor.bot.commands.ListItemsCommand;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class WishListBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    private final WishlistItemRepository wishlistItemRepository;

    /**
     * telegram ID -> current user's command
     */
    private final Map<String, BotCommand> currentCommands = new HashMap<>();

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
            String userId = String.valueOf(message.getChatId());
            var text = message.getText();

            var command = currentCommands.get(userId);
            if (command != null && command.isFinished()) {
                currentCommands.remove(userId);
                command = null;
            }

            log.info("Ensuring user exists: ID {}", userId);
            var user = userRepository.ensure(String.valueOf(chat.getId()), chat.getUserName(), chat.getFirstName(), chat.getLastName());

            if (text.startsWith("/start")) {
                handleStart(message);
            } else if (text.startsWith("/create")) {
                currentCommands.put(
                        userId,
                        new CreateWishlistItemCommand(this, message.getChatId(), user, wishlistItemRepository)
                                .start());
            } else if (text.startsWith("/cancel")) {
                if (command != null) {
                    command.cancel();
                } else {
                    reply(message, "There is nothing to cancel");
                }
            } else if (text.startsWith("/list")) {
                new ListItemsCommand(this, message.getChatId(), user, wishlistItemRepository).start();
            } else {
                if (command != null) {
                    command.text(text);
                } else {
                    reply(message, "Say `/create` or `/list`");
                }
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


    @Override
    public String getBotUsername() {
        return "wishlistBot";
    }
}
