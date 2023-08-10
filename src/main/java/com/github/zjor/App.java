package com.github.zjor;

import com.github.zjor.bot.WishListBot;
import com.github.zjor.ext.unirest.UnirestLoggingInterceptor;
import com.github.zjor.job.ExtractMetaTagsJob;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@SpringBootApplication
public class App {

    public static void main(String[] args) throws TelegramApiException {
        Unirest.config()
                .connectTimeout(15000)
                .socketTimeout(60000)
                .concurrency(10, 5)
                .interceptor(new UnirestLoggingInterceptor());

        ApplicationContext context = SpringApplication.run(App.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        WishListBot bot = context.getBean(WishListBot.class);
        botsApi.registerBot(bot);

        var extractMetaTagsJob = context.getBean(ExtractMetaTagsJob.class);
        extractMetaTagsJob.extractAndSaveMeta();
    }
}
