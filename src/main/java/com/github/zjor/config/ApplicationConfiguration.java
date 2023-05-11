package com.github.zjor.config;

import com.github.zjor.bot.WishListBot;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public WishListBot wishListBot(
            @Value("${telegram.botToken}") String token,
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository,
            @Value("${telegram.webAppUrl}") String webAppUrl) {
        return new WishListBot(token, userRepository, wishlistItemRepository, webAppUrl);
    }

}
