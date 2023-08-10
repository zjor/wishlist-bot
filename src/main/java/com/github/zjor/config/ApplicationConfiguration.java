package com.github.zjor.config;

import com.github.zjor.bot.TelegramApiClient;
import com.github.zjor.bot.WishListBot;
import com.github.zjor.integrations.opengraph.OpenGraphClient;
import com.github.zjor.job.ExtractMetaTagsJob;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.repository.jooq.WishlistItemJooqRepo;
import com.github.zjor.service.MetaResolverService;
import com.github.zjor.service.ReferralUrlService;
import com.github.zjor.service.UserSearchService;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public WishListBot wishListBot(
            @Value("${telegram.botToken}") String token,
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository,
            @Value("${telegram.webAppUrl}") String webAppUrl,
            ReferralUrlService referralUrlService,
            ApplicationEventPublisher eventPublisher) {
        return new WishListBot(token, userRepository, wishlistItemRepository, webAppUrl, referralUrlService, eventPublisher);
    }

    @Bean
    public TelegramApiClient telegramApiClient(@Value("${telegram.botToken}") String token) {
        return new TelegramApiClient(token);
    }

    @Bean
    public OpenGraphClient openGraphClient(@Value("${opengraph.apiKey}") String apiKey) {
        return new OpenGraphClient(apiKey);
    }

    @Bean
    public ExtractMetaTagsJob extractMetaTagsJob(
            WishlistItemRepository itemRepository,
            WishlistItemMetaRepository metaRepository,
            MetaResolverService metaResolverService) {
        return new ExtractMetaTagsJob(itemRepository, metaRepository, metaResolverService);
    }

    @Bean
    public MetaResolverService metaResolverService(
            WishlistItemRepository itemRepository,
            WishlistItemMetaRepository metaRepository,
            OpenGraphClient openGraphClient,
            ApplicationEventPublisher eventPublisher) {
        return new MetaResolverService(openGraphClient, itemRepository, metaRepository, eventPublisher);
    }

    @Bean
    public DSLContext dslContext(DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Bean
    public WishlistItemJooqRepo wishlistItemJooqRepo(DSLContext dsl) {
        return new WishlistItemJooqRepo(dsl);
    }

    @Bean
    public UserSearchService userSearchService(DSLContext dsl) {
        return new UserSearchService(dsl);
    }

    @Bean
    public ReferralUrlService referralUrlService(
            @Value("${referral.amazon}") String amazonStoreId) {
        return new ReferralUrlService(amazonStoreId);
    }

}
