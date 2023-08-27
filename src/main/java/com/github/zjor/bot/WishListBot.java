package com.github.zjor.bot;

import com.github.zjor.bot.commands.BotCommand;
import com.github.zjor.bot.commands.CreateWishlistItemCommand;
import com.github.zjor.bot.commands.ListItemsCommand;
import com.github.zjor.bot.commands.ViewItemCommand;
import com.github.zjor.domain.User;
import com.github.zjor.events.BotStartedEvent;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.service.ReferralUrlService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class WishListBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final String webAppUrl;
    private final ReferralUrlService referralUrlService;

    /**
     * telegram ID -> current user's command
     */
    private final Map<String, BotCommand> currentCommands = new HashMap<>();

    private final ApplicationEventPublisher eventPublisher;

    public WishListBot(
            String botToken,
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository,
            String webAppUrl,
            ReferralUrlService referralUrlService,
            ApplicationEventPublisher eventPublisher) {
        super(botToken);
        this.userRepository = userRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.webAppUrl = webAppUrl;
        this.referralUrlService = referralUrlService;
        this.eventPublisher = eventPublisher;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessageUpdate(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        } else {
            log.info("Unsupported update: {}", update);
        }
    }

    private void handleMessageUpdate(Message message) {
        var chat = message.getChat();
        var chatId = message.getChatId();
        String userId = String.valueOf(message.getChatId());
        var text = message.getText();

        var command = currentCommands.get(userId);
        if (command != null && command.isFinished()) {
            currentCommands.remove(userId);
            command = null;
        }

        // TODO: ensure only on start command
        log.info("Ensuring user exists: ID {}", userId);
        var user = userRepository.ensure(String.valueOf(chatId), chat.getUserName(), chat.getFirstName(), chat.getLastName());

        if (text.startsWith("/start")) {
            handleStart(message);
        } else if (text.startsWith("/create")) {
            initItemCreation(user);
        } else if (text.startsWith("/cancel")) {
            if (command != null) {
                command.cancel();
            } else {
                reply(message, "There is nothing to cancel");
            }
        } else if (text.startsWith("/list")) {
            new ListItemsCommand(this, chatId, user, wishlistItemRepository, webAppUrl).start();
        } else if (text.startsWith("/view")) {
            currentCommands.put(
                    userId,
                    new ViewItemCommand(this, chatId, text, user, wishlistItemRepository)
                            .start());
        } else {
            if (command != null) {
                command.text(text);
            } else {
                reply(message, "Say `/create` or `/list`");
            }
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        var data = callbackQuery.getData();
        log.info("Callback : {}", callbackQuery.getData());
        var tokens = data.split(":");
        var itemId = tokens[0];
        var command = tokens[1];
        if (command.equals("toggle_public")) {
            var item = wishlistItemRepository.findById(itemId).get();
            item.setPublic(!item.isPublic());
            wishlistItemRepository.save(item);
            // TODO: reply
        }
    }

    private void reply(Message message, String text) {
        reply(message, text, false);
    }

    @SneakyThrows
    private void reply(Message message, String text, boolean markdown) {
        var builder = SendMessage.builder().chatId(message.getChatId()).text(text);
        if (markdown) {
            builder.parseMode(ParseMode.MARKDOWN);
        }
        execute(builder.build());
    }

    @SneakyThrows
    private void handleStart(Message message) {
        eventPublisher.publishEvent(BotStartedEvent.of(message.getChat()));
        reply(message, Responses.start(message.getChat().getFirstName()), true);
    }


    @Override
    public String getBotUsername() {
        return "wishlistBot";
    }

    public void initItemCreation(User user) {
        currentCommands.put(
                user.getExtId(),
                new CreateWishlistItemCommand(this, Long.valueOf(user.getExtId()), user, wishlistItemRepository, eventPublisher, referralUrlService)
                        .start());
    }
}
