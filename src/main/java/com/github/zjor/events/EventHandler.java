package com.github.zjor.events;

import com.github.zjor.domain.WishlistItemMeta;
import com.github.zjor.integrations.opengraph.OpenGraphClient;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//TODO: split into separate classes
@Slf4j
public class EventHandler {

    private final UserRepository userRepository;
    private final WishlistItemRepository itemRepository;
    private final WishlistItemMetaRepository metaRepository;
    private final OpenGraphClient openGraphClient;
    private final ApplicationEventPublisher eventPublisher;
    private final DefaultAbsSender telegramSender;

    public EventHandler(
            UserRepository userRepository, WishlistItemRepository itemRepository,
            WishlistItemMetaRepository metaRepository,
            OpenGraphClient openGraphClient,
            ApplicationEventPublisher eventPublisher,
            DefaultAbsSender telegramSender) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.metaRepository = metaRepository;
        this.openGraphClient = openGraphClient;
        this.eventPublisher = eventPublisher;
        this.telegramSender = telegramSender;
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
        var item = itemRepository.findById(e.itemId()).get();
        if (!UrlUtils.isValidUrl(item.getUrl())) {
            log.warn("Not valid URL: {}", item.getUrl());
            return;
        }

        try {
            var response = openGraphClient.fetchOpenGraph(item.getUrl(), false);
            if (response.success) {
                var data = response.data.get().getHybridGraph();
                log.info("Saving meta: {}", data);
                metaRepository.save(WishlistItemMeta.builder()
                        .item(item)
                        .title(data.getTitle())
                        .description(data.getDescription())
                        .imageUrl(data.getImage())
                        .raw(response.raw)
                        .build());
                eventPublisher.publishEvent(new MetaExtractedEvent(e.userId(), e.itemId()));
            } else {
                metaRepository.save(WishlistItemMeta.builder()
                        .item(item)
                        .raw(response.raw)
                        .isError(true)
                        .errorMessage(response.exception.map(Exception::getMessage).orElse(null))
                        .build());
            }
        } catch (Exception ex) {
            log.error("Failed to extract tags for item. ID: " + item.getId(), ex);
            metaRepository.save(WishlistItemMeta.builder()
                    .item(item)
                    .isError(true)
                    .errorMessage(ex.getMessage())
                    .build());
        }
    }

    @Async
    @EventListener
    public void onMetaExtracted(MetaExtractedEvent e) {
        log.info("[onMetaExtracted]: {}", e);
        try {
            var user = userRepository.findById(e.userId()).get();
            telegramSender.execute(SendMessage.builder()
                            .chatId(user.getExtId())
                            .text("Metadata extracted!")
                    .build());
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }

    }

}
