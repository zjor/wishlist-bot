package com.github.zjor.controller;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/wishlist/public")
public class PublicWishlistController {

    private final WishlistItemRepository wishlistItemRepository;

    public PublicWishlistController(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @GetMapping
    public List<WishlistItem> getPublicItems() {
        return wishlistItemRepository.findWishlistItemByIsPublicOrderByCreatedAtDesc(true);
    }

}
