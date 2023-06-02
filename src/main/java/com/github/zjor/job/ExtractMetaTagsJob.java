package com.github.zjor.job;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.service.MetaResolverService;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ExtractMetaTagsJob {

    private final WishlistItemRepository itemRepository;
    private final WishlistItemMetaRepository metaRepository;
    private final MetaResolverService metaResolverService;

    public ExtractMetaTagsJob(
            WishlistItemRepository itemRepository,
            WishlistItemMetaRepository metaRepository,
            MetaResolverService metaResolverService) {
        this.itemRepository = itemRepository;
        this.metaRepository = metaRepository;
        this.metaResolverService = metaResolverService;
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
            metaResolverService.resolveAndStoreMeta(item.getId());
        }
    }
}
