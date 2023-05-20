package com.github.zjor;

import com.github.zjor.bot.WishListBot;
import com.github.zjor.ext.unirest.UnirestLoggingInterceptor;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@SpringBootApplication
public class App {

    private void executeSql(JdbcTemplate db) {
        var query = """
                select * from wishlist_items wi
                    where not exists(select 1 from wishlist_items_meta wim where wim.item_id = wi.id);                                
                """;
        db.query(query, rs -> {
            var meta = rs.getMetaData();
            var cols = meta.getColumnCount();
            while (rs.next()) {
                var sb = new StringBuilder();
                for (int i = 1; i <= cols; i++) {
                    sb.append(rs.getString(i)).append("; ");
                }
                log.info("{}", sb);
            }
        });

    }

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

//        var userRepo = context.getBean(UserRepository.class);
//        userRepo.findAll().forEach(u -> {
//            System.out.println(u);
//        });
//
//        var extractMetaTagsJob = context.getBean(ExtractMetaTagsJob.class);
//        extractMetaTagsJob.extractAndSaveMeta();
        App app = context.getBean(App.class);
        app.executeSql(context.getBean(JdbcTemplate.class));
    }
}
