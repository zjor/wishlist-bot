package com.github.zjor.config;

import com.cloudinary.Cloudinary;
import com.github.zjor.bot.TelegramApiClient;
import com.github.zjor.events.EventHandler;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.service.MetaResolverService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class EventConfiguration {

    @Bean
    public Cloudinary cloudinary(@Value("${cloudinary.url}") String cloudinaryUrl) {
        return new Cloudinary(cloudinaryUrl);
    }

    @Bean
    public EventHandler eventHandler(
            UserRepository userRepository,
            TelegramApiClient telegramApiClient,
            MetaResolverService metaResolverService,
            Cloudinary cloudinary
    ) {
        return new EventHandler(userRepository, telegramApiClient, metaResolverService, cloudinary);
    }

}
