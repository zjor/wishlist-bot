package com.github.zjor.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.UserProfilePhotos;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TelegramApiClient extends DefaultAbsSender {
    public TelegramApiClient(String token) {
        super(new DefaultBotOptions(), token);
    }

    @SneakyThrows
    public Optional<InputStream> getUserProfilePhotoInputStream(long userId) {
        UserProfilePhotos photos = execute(new GetUserProfilePhotos(userId));
        if (photos.getTotalCount() > 0) {
            List<PhotoSize> sizes = photos.getPhotos().get(0);
            if (!sizes.isEmpty()) {
                PhotoSize size = sizes.get(0);
                log.info("Downloading photo: {}", size);
                var file = execute(new GetFile(size.getFileId()));
                return Optional.of(downloadFileAsStream(file));
            }
        }
        return Optional.empty();
    }

}
