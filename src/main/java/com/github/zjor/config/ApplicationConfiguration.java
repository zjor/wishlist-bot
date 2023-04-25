package com.github.zjor.config;

import com.github.zjor.WishListBot;
import com.github.zjor.repository.FileUserRepositoryImpl;
import com.github.zjor.repository.UserRepository;
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
    public WishListBot wishListBot(
            @Value("${telegram.botToken}") String token,
            UserRepository userRepository) {
        return new WishListBot(token, userRepository);
    }

}
