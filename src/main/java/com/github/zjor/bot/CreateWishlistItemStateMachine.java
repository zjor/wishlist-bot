package com.github.zjor.bot;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.util.ListUtils;
import lombok.Getter;

public class CreateWishlistItemStateMachine {

    public enum State {
        STARTED,
        PENDING_NAME,
        PENDING_DESCRIPTION,
        PENDING_URL,
        PENDING_TAGS,
        DONE(true),
        CANCELLED(true);

        @Getter
        private final boolean isFinal;

        State() {
            this.isFinal = false;
        }

        State(boolean isFinal) {
            this.isFinal = isFinal;
        }
    }

    @Getter
    private State state = State.STARTED;

    @Getter
    private WishlistItem.WishlistItemBuilder context = WishlistItem.builder();

    public TransitionResult start() {
        return transition(new StartAction());
    }

    public TransitionResult cancel() {
        return transition(new CancelAction());
    }

    public TransitionResult text(String text) {
        return transition(new TextAction(text));
    }

    public TransitionResult transition(Action action) {
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
        return new TransitionResult(state, replyText);
    }

    public interface Action {
    }

    public static class StartAction implements Action {
    }

    public record TextAction(String text) implements Action {
    }

    public static class CancelAction implements Action {
    }

    public record TransitionResult(State state, String replyText) {

    }

}
