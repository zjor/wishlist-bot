package com.github.zjor.controller;

import com.github.zjor.controller.dto.JWishlistItem;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static com.github.zjor.controller.ControllerUtils.notFoundSupplier;
import static com.github.zjor.controller.XHttpHeaders.X_TELEGRAM_USER;

@RestController
@RequestMapping("api/wishlist/private")
public class PrivateWishlistController {

    private final UserRepository userRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistItemMetaRepository wishlistItemMetaRepository;

    public PrivateWishlistController(
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository,
            WishlistItemMetaRepository wishlistItemMetaRepository) {
        this.userRepository = userRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistItemMetaRepository = wishlistItemMetaRepository;
    }

    @GetMapping
    public List<JWishlistItem> getItems(@RequestHeader(X_TELEGRAM_USER) String telegramId) {
        var user = userRepository.findUserByExtId(telegramId).orElseThrow(notFoundSupplier(telegramId));
        var items = wishlistItemRepository.findByOwner(user);
        var result = new LinkedList<JWishlistItem>();
        for (WishlistItem item: items) {
            var meta = wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item);
            result.add(JWishlistItem.Convert.build(item, meta));
        }
        return result;
    }

}
