package com.github.zjor.events;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.github.zjor.bot.TelegramApiClient;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.service.MetaResolverService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.Map;

//TODO: split into separate classes
@Slf4j
public class EventHandler {

    private final UserRepository userRepository;
    private final TelegramApiClient telegramClient;
    private final MetaResolverService metaResolverService;

    private final Cloudinary cloudinary;

    public EventHandler(
            UserRepository userRepository,
            TelegramApiClient telegramClient,
            MetaResolverService metaResolverService,
            Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.telegramClient = telegramClient;
        this.metaResolverService = metaResolverService;
        this.cloudinary = cloudinary;
    }

    @Async
    @EventListener
    public void onBotStarted(BotStartedEvent event) {
        log.info("[onBotStarted] {}: {}", Thread.currentThread().getName(), event);
        var opt = telegramClient.getUserProfilePhotoInputStream(event.telegramId());
        if (opt.isPresent()) {
            var imageStream = opt.get();
            Map params = ObjectUtils.asMap(
                    "use_filename", false,
                    "unique_filename", true,
                    "overwrite", false
            );
            try {
                var imageData = IOUtils.toByteArray(imageStream);
                var uploaded = cloudinary.uploader().upload(imageData, params);
                var imageUrl = (String) uploaded.get("secure_url");
                log.info("Image URL: {}", imageUrl);

                userRepository.findUserByExtId(String.valueOf(event.telegramId()))
                        .ifPresent(user -> {
                            user.setImageUrl(imageUrl);
                            userRepository.save(user);
                        });
            } catch (IOException ex) {
                log.error("Failed to upload an image: " + ex.getMessage(), ex);
            }
        }
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
        telegramClient.execute(SendMessage.builder()
                .chatId(user.getExtId())
                .text("Metadata extracted for the item ID: " + e.itemId())
                .build());
    }

}
