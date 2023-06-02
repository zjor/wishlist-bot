package com.github.zjor.config;

import com.github.zjor.bot.TelegramApiClient;
import com.github.zjor.events.EventHandler;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.service.MetaResolverService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class EventConfiguration {

    @Bean
    public EventHandler eventHandler(
            UserRepository userRepository,
            TelegramApiClient telegramApiClient,
            MetaResolverService metaResolverService
    ) {
        return new EventHandler(userRepository, telegramApiClient, metaResolverService);
    }

}
