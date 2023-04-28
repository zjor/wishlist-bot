package com.github.zjor;

import com.github.zjor.bot.WishListBot;
import com.github.zjor.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class App {

    public static void main(String[] args) throws TelegramApiException {
        ApplicationContext context = SpringApplication.run(App.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        WishListBot bot = context.getBean(WishListBot.class);
        botsApi.registerBot(bot);

        var userRepo = context.getBean(UserRepository.class);
        userRepo.findAll().forEach(u -> {
            System.out.println(u);
        });
    }
}
