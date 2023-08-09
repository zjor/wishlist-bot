package com.github.zjor.controller;

import com.github.zjor.controller.dto.JPublicListWishlistItem;
import com.github.zjor.domain.ItemStatus;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.domain.jooq.tables.records.WishlistItemsRecord;
import com.github.zjor.ext.spring.aop.Log;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import com.github.zjor.repository.jooq.WishlistItemJooqRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/wishlist/public")
public class PublicWishlistController {

    private final UserRepository userRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistItemMetaRepository wishlistItemMetaRepository;
    private final WishlistItemJooqRepo wishlistItemJooqRepo;

    public PublicWishlistController(
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository,
            WishlistItemMetaRepository wishlistItemMetaRepository,
            WishlistItemJooqRepo wishlistItemJooqRepo) {
        this.userRepository = userRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistItemMetaRepository = wishlistItemMetaRepository;
        this.wishlistItemJooqRepo = wishlistItemJooqRepo;
    }

    @Log
    @GetMapping
    public List<JPublicListWishlistItem> getPublicItems() {
        var items = wishlistItemRepository.findWishlistItemByIsPublicOrderByCreatedAtDesc(true);

        var result = new LinkedList<JPublicListWishlistItem>();
        for (WishlistItem item: items) {
            if (item.getStatus().isTerminal) {
                continue;
            }
            var meta = wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item);
            result.add(JPublicListWishlistItem.Converter.build(item, meta));
        }
        return result;
    }

    // TODO: sometimes JOOQ does not get connection to DB causing this endpoint to stuck for 30 seconds and fail
    @Log
    @GetMapping("{extId}")
    public List<JPublicListWishlistItem> getUserPublicItems(@PathVariable("extId") String extId) {
        var userOpt = userRepository.findUserByExtId(extId);
        if (userOpt.isEmpty()) {
            return List.of();
        }

        var items = wishlistItemJooqRepo.getPublicItemsByUserId(userOpt.get().getId());
        var result = new LinkedList<JPublicListWishlistItem>();

        for (WishlistItemsRecord item: items) {
            if (ItemStatus.valueOf(item.getStatus()).isTerminal) {
                continue;
            }
            var meta = wishlistItemJooqRepo.getItemMetaByItemId(item.getId());
            result.add(JPublicListWishlistItem.Converter.build(item, meta));
        }
        return result;
    }

}
