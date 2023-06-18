package com.github.zjor.controller;

import com.github.zjor.controller.dto.JSetIsPublicRequest;
import com.github.zjor.controller.dto.JSetStatusRequest;
import com.github.zjor.controller.dto.JWishlistItem;
import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.ext.spring.auth.AuthUser;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

import static com.github.zjor.controller.ControllerUtils.notFoundSupplier;
import static com.github.zjor.controller.ControllerUtils.unauthorized;

@RestController
@RequestMapping("api/wishlist/private")
public class PrivateWishlistController {

    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistItemMetaRepository wishlistItemMetaRepository;

    public PrivateWishlistController(
            WishlistItemRepository wishlistItemRepository,
            WishlistItemMetaRepository wishlistItemMetaRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistItemMetaRepository = wishlistItemMetaRepository;
    }

    @GetMapping
    public List<JWishlistItem> getItems(@AuthUser User user) {
        var items = wishlistItemRepository.findByOwner(user);
        var result = new LinkedList<JWishlistItem>();
        for (WishlistItem item: items) {
            var meta = wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item);
            result.add(JWishlistItem.Convert.build(item, meta));
        }
        return result;
    }

    @GetMapping("{id}")
    public JWishlistItem get(@PathVariable("id") String id, @AuthUser User user) {
        var item = wishlistItemRepository.findById(id).orElseThrow(notFoundSupplier(id));
        if (!item.getOwner().equals(user)) {
            throw unauthorized(id);
        }
        return JWishlistItem.Convert.build(
                item,
                wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item));
    }

    @PostMapping("{id}/status")
    public JWishlistItem setStatus(@PathVariable("id") String id,
                                   @RequestBody JSetStatusRequest req,
                                   @AuthUser User user) {
        var item = wishlistItemRepository.findById(id).orElseThrow(notFoundSupplier(id));
        if (!item.getOwner().equals(user)) {
            throw unauthorized(id);
        }

        // TODO: check transition table
        item.setStatus(req.getStatus());
        item = wishlistItemRepository.save(item);

        return JWishlistItem.Convert.build(
                item,
                wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item));
    }

    @PostMapping("{id}/is-public")
    public JWishlistItem setIsPublic(@PathVariable("id") String id,
                                     @RequestBody JSetIsPublicRequest req,
                                     @AuthUser User user) {
        var item = wishlistItemRepository.findById(id).orElseThrow(notFoundSupplier(id));
        if (!item.getOwner().equals(user)) {
            throw unauthorized(id);
        }
        item.setPublic(req.isPublic());
        item = wishlistItemRepository.save(item);

        return JWishlistItem.Convert.build(
                item,
                wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item));
    }

}
