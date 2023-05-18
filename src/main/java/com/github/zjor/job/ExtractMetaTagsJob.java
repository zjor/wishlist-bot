package com.github.zjor.job;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.domain.WishlistItemMeta;
import com.github.zjor.integrations.opengraph.OpenGraphClient;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ExtractMetaTagsJob {

    private final WishlistItemRepository itemRepository;
    private final WishlistItemMetaRepository metaRepository;
    private final OpenGraphClient openGraphClient;

    public ExtractMetaTagsJob(
            WishlistItemRepository itemRepository,
            WishlistItemMetaRepository metaRepository,
            OpenGraphClient openGraphClient) {
        this.itemRepository = itemRepository;
        this.metaRepository = metaRepository;
        this.openGraphClient = openGraphClient;
    }

    // TODO: rewrite with CriteriaBuilder or JOOQ
    public List<WishlistItem> findItemsWithMissingMeta() {
        List<WishlistItem> itemsWithoutMeta = new LinkedList<>();
        for (WishlistItem item : itemRepository.findAll()) {
            var opt = metaRepository.findFirstByItemOrderByCreatedAtDesc(item);
            if (opt.isEmpty()) {
                itemsWithoutMeta.add(item);
            }
        }
        return itemsWithoutMeta;
    }

    public void extractAndSaveMeta() {
        for (WishlistItem item : findItemsWithMissingMeta()) {
            if (!UrlUtils.isValidUrl(item.getUrl())) {
                continue;
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
                } else {
                    metaRepository.save(WishlistItemMeta.builder()
                            .item(item)
                            .raw(response.raw)
                            .isError(true)
                            .errorMessage(response.exception.map(Exception::getMessage).orElse(null))
                            .build());
                }
            } catch (Exception e) {
                log.error("Failed to extract tags for item. ID: " + item.getId(), e);
                metaRepository.save(WishlistItemMeta.builder()
                        .item(item)
                        .isError(true)
                        .errorMessage(e.getMessage())
                        .build());
            }
        }
    }
}
