package com.github.zjor.config;

import com.github.zjor.ext.spring.auth.AuthUser;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.zjor.controller.XHttpHeaders.X_TELEGRAM_USER;

@Configuration
public class SwaggerConfiguration {

    public static final String SECURITY_REQUIREMENT_BASIC = "basic";

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthUser.class);
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(X_TELEGRAM_USER,
                                new SecurityScheme()
                                        .in(SecurityScheme.In.HEADER)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .name(X_TELEGRAM_USER)
                                        .description("Telegram ID of an authenticated user"))
                        .addSecuritySchemes(SECURITY_REQUIREMENT_BASIC,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")))
                .info(new Info().title("Wishlist Bot API")
                        .description("Wishlist Telegram Bot")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("@wisshlist_bot")
                        .url("https://t.me/wisshlist_bot"));
    }
}
