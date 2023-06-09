package com.github.zjor.controller;

import com.github.zjor.controller.dto.JPublicListWishlistItem;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/wishlist/public")
public class PublicWishlistController {

    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistItemMetaRepository wishlistItemMetaRepository;

    public PublicWishlistController(
            WishlistItemRepository wishlistItemRepository,
            WishlistItemMetaRepository wishlistItemMetaRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistItemMetaRepository = wishlistItemMetaRepository;
    }

    @GetMapping
    public List<JPublicListWishlistItem> getPublicItems() {
        var items = wishlistItemRepository.findWishlistItemByIsPublicOrderByCreatedAtDesc(true);
        var result = new LinkedList<JPublicListWishlistItem>();
        for (WishlistItem item: items) {
            var meta = wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item);
            result.add(JPublicListWishlistItem.Converter.build(item, meta));
        }
        return result;
    }

}
