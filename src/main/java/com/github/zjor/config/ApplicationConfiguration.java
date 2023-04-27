package com.github.zjor.config;

import com.github.zjor.bot.WishListBot;
import com.github.zjor.repository.FileUserRepositoryImpl;
import com.github.zjor.repository.FileWishlistItemRepositoryImpl;
import com.github.zjor.repository.JPAUserRepository;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public UserRepository userRepository() {
        return new FileUserRepositoryImpl("./storage/users.json");
    }

    @Bean
    public WishlistItemRepository wishlistItemRepository() {
        return new FileWishlistItemRepositoryImpl("./storage/wishlistItems.json");
    }

    @Bean
    public WishListBot wishListBot(
            @Value("${telegram.botToken}") String token,
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository,
            JPAUserRepository jpaUserRepository
            ) {
        return new WishListBot(token, userRepository, wishlistItemRepository, jpaUserRepository);
    }

}
