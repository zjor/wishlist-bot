package com.github.zjor.controller.admin;

import com.github.zjor.bot.TelegramApiClient;
import com.github.zjor.events.BotStartedEvent;
import com.github.zjor.ext.spring.aop.Log;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

import static com.github.zjor.config.SwaggerConfiguration.SECURITY_REQUIREMENT_BASIC;

@Slf4j
@RestController
@RequestMapping("admin/api")
@SecurityRequirements({@SecurityRequirement(name = SECURITY_REQUIREMENT_BASIC)})
public class AdminController {

    private final ApplicationEventPublisher eventPublisher;
    private final TelegramApiClient telegramApiClient;

    public AdminController(
            ApplicationEventPublisher eventPublisher,
            TelegramApiClient telegramApiClient) {
        this.eventPublisher = eventPublisher;
        this.telegramApiClient = telegramApiClient;
    }

    @Log
    @PostMapping("event")
    public void emitEvent(@RequestBody EmitEventRequest req) {
        if (req.type == EmitEventRequest.EventType.BOT_STARTED_EVENT) {

            // TODO: parse properly
            eventPublisher.publishEvent(new BotStartedEvent(
                    Long.valueOf((String) req.data.get("telegramId")),
                    null,
                    null,
                    null));
        }
    }

    @Log
    @PostMapping("message")
    public void sendPrivateMessage(@RequestBody SendPrivateMessageRequest req) {
        try {
            telegramApiClient.execute(SendMessage.builder()
                            .chatId(req.to)
                            .text(req.message)
                            .parseMode(req.mode)
                    .build());
        } catch (TelegramApiException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    public record EmitEventRequest(EventType type, Map<String, Object> data) {
        public enum EventType {
            BOT_STARTED_EVENT
        }
    }

    public record SendPrivateMessageRequest(long to, String message, String mode) {
    }

}
