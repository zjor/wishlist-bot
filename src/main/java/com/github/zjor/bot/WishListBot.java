package com.github.zjor.bot;

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
     * telegram ID -> is creating new item
     */
    private final Map<String, Boolean> userState = new HashMap<>();

    /**
     * telegram ID -> item creation state machine
     */
    private final Map<String, CreateWishlistItemStateMachine> userFsm = new HashMap<>();

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

            log.info("Ensuring user exists: ID {}", userId);
            var user = userRepository.ensure(String.valueOf(chat.getId()), chat.getUserName(), chat.getFirstName(), chat.getLastName());

            if (text.startsWith("/start")) {
                handleStart(message);
            } else if (text.startsWith("/create")) {
                CreateWishlistItemStateMachine stateMachine = new CreateWishlistItemStateMachine();
                userFsm.put(userId, stateMachine);
                var result = stateMachine.start();
                reply(message, result.replyText());
            } else if (text.startsWith("/cancel")) {
                var stateMachine = userFsm.get(userId);
                if (stateMachine != null) {
                    var result = stateMachine.cancel();
                    userFsm.remove(userId);
                    reply(message, result.replyText());
                } else {
                    reply(message, "There is nothing to cancel");
                }
            } else if (text.startsWith("/list")) {
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
                if (sb.isEmpty()) {
                    sb.append("No items yet, please type `/create` to add");
                }
                reply(message, sb.toString());
            } else {
                var stateMachine = userFsm.get(userId);
                if (stateMachine != null) {
                    var result = stateMachine.text(text);
                    if (result.state() == CreateWishlistItemStateMachine.State.DONE) {
                        var item = stateMachine.getContext().build();
                        wishlistItemRepository.create(
                                user,
                                item.getName(),
                                item.getDescription(),
                                item.getImageUrl(),
                                item.getUrl(),
                                item.getTags());
                        userFsm.remove(userId);
                    }
                    reply(message, result.replyText());
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
