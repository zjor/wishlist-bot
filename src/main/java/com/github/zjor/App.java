package com.github.zjor;

import com.github.zjor.repository.FileUserRepositoryImpl;
import com.github.zjor.repository.UserRepository;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        String token = "1210082003:AAG30Mv9s4BDw6a5Zs_AcG26r1LsHfJrQc4";
        UserRepository userRepository = new FileUserRepositoryImpl("./storage/users.json");
        botsApi.registerBot(new WishListBot(token, userRepository));
    }
}
