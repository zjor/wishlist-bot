package com.github.zjor.service;

import com.github.zjor.domain.WishlistItemMeta;
import com.github.zjor.events.MetaExtractedEvent;
import com.github.zjor.ext.spring.aop.Log;
import com.github.zjor.integrations.opengraph.OpenGraphClient;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class MetaResolverService {

    private final OpenGraphClient openGraphClient;
    private final WishlistItemRepository itemRepository;
    private final WishlistItemMetaRepository metaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MetaResolverService(
            OpenGraphClient openGraphClient,
            WishlistItemRepository itemRepository,
            WishlistItemMetaRepository metaRepository,
            ApplicationEventPublisher eventPublisher) {
        this.openGraphClient = openGraphClient;
        this.itemRepository = itemRepository;
        this.metaRepository = metaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Log
    @Async
    public void resolveAndStoreMeta(String itemId) {
        var itemOpt = itemRepository.findById(itemId);
        if (itemOpt.isEmpty()) {
            log.warn("Wishlist item not found. ID: {}", itemId);
            return;
        }
        var item = itemOpt.get();

        if (!UrlUtils.isValidUrl(item.getUrl())) {
            log.warn("Not valid URL: {}", item.getUrl());
            return;
        }

        try {
            var response = openGraphClient.fetchOpenGraph(item.getUrl(), false);
            if (response.success) {
                var data = response.data.get().getHybridGraph();
                log.info("Saving meta: {}", data);
                metaRepository.save(WishlistItemMeta.builder()
                        .item(item)
                        .title(data.getTitle())
                        .description(data.getDescription())
                        .imageUrl(data.getImage())
                        .raw(response.raw)
                        .build());
                eventPublisher.publishEvent(new MetaExtractedEvent(item.getOwner().getId(), item.getId()));
            } else {
                var errorMessage = response.exception.map(Exception::getMessage).orElse(null);
                log.warn("Failed to extract tags for item. ID: {}; reason: {}",
                        item.getId(),
                        errorMessage);
                metaRepository.save(WishlistItemMeta.builder()
                        .item(item)
                        .raw(response.raw)
                        .isError(true)
                        .errorMessage(errorMessage)
                        .build());
            }
        } catch (Exception ex) {
            log.error("Failed to extract tags for item. ID: " + item.getId(), ex);
            metaRepository.save(WishlistItemMeta.builder()
                    .item(item)
                    .isError(true)
                    .errorMessage(ex.getMessage())
                    .build());
        }

    }

}
