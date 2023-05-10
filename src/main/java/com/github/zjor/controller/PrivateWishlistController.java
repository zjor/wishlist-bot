package com.github.zjor.controller;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.repository.UserRepository;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.zjor.controller.ControllerUtils.notFoundSupplier;
import static com.github.zjor.controller.XHttpHeaders.X_TELEGRAM_USER;

@RestController
@RequestMapping("api/wishlist/private")
public class PrivateWishlistController {

    private final UserRepository userRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public PrivateWishlistController(
            UserRepository userRepository,
            WishlistItemRepository wishlistItemRepository) {
        this.userRepository = userRepository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @GetMapping
    public List<WishlistItem> getItems(@RequestHeader(X_TELEGRAM_USER) String telegramId) {
        var user = userRepository.findUserByExtId(telegramId).orElseThrow(notFoundSupplier(telegramId));
        return wishlistItemRepository.findByOwner(user);
    }

}
