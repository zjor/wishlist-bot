package com.github.zjor.events;

import com.github.zjor.repository.UserRepository;
import com.github.zjor.service.MetaResolverService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

//TODO: split into separate classes
@Slf4j
public class EventHandler {

    private final UserRepository userRepository;
    private final DefaultAbsSender telegramSender;
    private final MetaResolverService metaResolverService;

    public EventHandler(
            UserRepository userRepository,
            DefaultAbsSender telegramSender,
            MetaResolverService metaResolverService) {
        this.userRepository = userRepository;
        this.telegramSender = telegramSender;
        this.metaResolverService = metaResolverService;
    }

    @Async
    @EventListener
    public void onBotStarted(BotStartedEvent e) {
        log.info("[onBotStarted] {}: {}", Thread.currentThread().getName(), e);
    }

    @Async
    @EventListener
    public void onItemCreated(WishlistItemCreatedEvent e) {
        log.info("[onItemCreated]: {}", e);
        metaResolverService.resolveAndStoreMeta(e.itemId());
    }

    @SneakyThrows
    @Async
    @EventListener
    public void onMetaExtracted(MetaExtractedEvent e) {
        log.info("[onMetaExtracted]: {}", e);
        var user = userRepository.findById(e.userId()).get();
        telegramSender.execute(SendMessage.builder()
                .chatId(user.getExtId())
                .text("Metadata extracted for the item ID: " + e.itemId())
                .build());
    }

}
