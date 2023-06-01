package com.github.zjor.config;

import com.github.zjor.events.EventHandler;
import com.github.zjor.integrations.opengraph.OpenGraphClient;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.bots.DefaultAbsSender;

@EnableAsync
@Configuration
public class EventConfiguration {

    @Bean
    public EventHandler eventHandler(
            UserRepository userRepository,
            WishlistItemRepository itemRepository,
            WishlistItemMetaRepository metaRepository,
            OpenGraphClient openGraphClient,
            ApplicationEventPublisher eventPublisher,
            DefaultAbsSender telegramSender
    ) {
        return new EventHandler(userRepository, itemRepository, metaRepository, openGraphClient, eventPublisher, telegramSender);
    }

}
