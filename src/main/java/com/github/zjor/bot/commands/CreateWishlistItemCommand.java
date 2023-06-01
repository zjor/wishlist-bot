package com.github.zjor.bot.commands;

import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.events.WishlistItemCreatedEvent;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.util.ListUtils;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;
import org.telegram.telegrambots.bots.DefaultAbsSender;

enum State {
    STARTED,
    PENDING_NAME,
    PENDING_DESCRIPTION,
    PENDING_URL,
    PENDING_TAGS,
    DONE(true),
    CANCELLED(true);

    @Getter
    final boolean isFinal;

    State() {
        this.isFinal = false;
    }

    State(boolean isFinal) {
        this.isFinal = isFinal;
    }
}

public class CreateWishlistItemCommand extends BotCommand {

    private final User user;
    private final WishlistItemRepository wishlistItemRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CreateWishlistItemCommand(
            DefaultAbsSender sender,
            Long chatId,
            User user,
            WishlistItemRepository wishlistItemRepository,
            ApplicationEventPublisher eventPublisher) {
        super(sender, chatId);
        this.user = user;
        this.wishlistItemRepository = wishlistItemRepository;
        this.eventPublisher = eventPublisher;
    }

    private State state = State.STARTED;

    @Getter
    private WishlistItem.WishlistItemBuilder context = WishlistItem.builder();

    public BotCommand start() {
        transition(new StartAction());
        return this;
    }

    public void cancel() {
        transition(new CancelAction());
    }

    public void text(String text) {
        transition(new TextAction(text));
        if (state == State.DONE) {
            var item = wishlistItemRepository.save(getContext().owner(user).build());
            eventPublisher.publishEvent(new WishlistItemCreatedEvent(user.getId(), item.getId()));
        }
    }

    @Override
    public boolean isFinished() {
        return state.isFinal;
    }

    public void transition(Action action) {
        String replyText = null;
        switch (action) {
            case StartAction ignored -> {
                state = State.PENDING_NAME;
                replyText = "Enter a name of the item";
            }
            case CancelAction ignored -> {
                state = State.CANCELLED;
                replyText = "Item creation was cancelled";
            }
            case TextAction textAction -> {
                var text = textAction.text.trim();
                switch (state) {
                    case PENDING_NAME -> {
                        context.name(text);
                        state = State.PENDING_DESCRIPTION;
                        replyText = "Enter the description";
                    }
                    case PENDING_DESCRIPTION -> {
                        context.description(text);
                        state = State.PENDING_URL;
                        replyText = "Enter the URL of your wishlist item";
                    }
                    case PENDING_URL -> {
                        context.url(text);
                        state = State.PENDING_TAGS;
                        replyText = "Enter tags separated by comma (,), e.g. `book, hobby`";
                    }
                    case PENDING_TAGS -> {
                        context.tags(ListUtils.parse(text));
                        state = State.DONE;
                        replyText = "All done! Thanks! Now type `/list` command";
                    }
                    case DONE -> replyText = "There is nothing more to add.";
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
        if (replyText != null) {
            reply(replyText);
        }
    }

    public interface Action {
    }

    public static class StartAction implements Action {
    }

    public record TextAction(String text) implements Action {
    }

    public static class CancelAction implements Action {
    }

}
