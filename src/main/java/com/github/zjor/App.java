package com.github.zjor;

import com.github.zjor.repository.FileUserRepositoryImpl;
import com.github.zjor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class App {

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

    public static void main(String[] args) throws TelegramApiException {
        ApplicationContext context = SpringApplication.run(App.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        WishListBot bot = context.getBean(WishListBot.class);
        botsApi.registerBot(bot);
    }
}
