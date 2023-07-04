package com.github.zjor.config;

import com.github.zjor.ext.spring.auth.AuthUser;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthUser.class);
    }

    @Bean
    public OpenAPI openApi() {

        // TODO: add X-Telegram-User header to private controllers
        return new OpenAPI()
                .info(new Info().title("Wishlist Bot API")
                        .description("Wishlist Telegram Bot")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("@wisshlist_bot")
                        .url("https://t.me/wisshlist_bot"));
    }
}
