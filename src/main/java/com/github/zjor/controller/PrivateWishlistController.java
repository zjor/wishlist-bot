package com.github.zjor.controller;

import com.github.zjor.controller.dto.JPrivateListWishlistItem;
import com.github.zjor.controller.dto.JPrivateWishlistItemDetails;
import com.github.zjor.controller.dto.JSetIsPublicRequest;
import com.github.zjor.controller.dto.JSetStatusRequest;
import com.github.zjor.domain.ItemStatus;
import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.ext.spring.auth.AuthUser;
import com.github.zjor.repository.WishlistItemMetaRepository;
import com.github.zjor.repository.WishlistItemRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.zjor.controller.ControllerUtils.badRequest;
import static com.github.zjor.controller.ControllerUtils.notFoundSupplier;
import static com.github.zjor.controller.ControllerUtils.unauthorized;
import static com.github.zjor.controller.XHttpHeaders.X_TELEGRAM_USER;

@SecurityRequirements({@SecurityRequirement(name = X_TELEGRAM_USER)})
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
    public List<JPrivateListWishlistItem> getItems(
            @RequestParam(value = "excludeStatuses", required = false) Set<ItemStatus> excludedStatuses,
            @AuthUser User user) {

        // TODO: filter by SQL
        var items = wishlistItemRepository.findByOwner(user).stream().filter(item ->
                excludedStatuses == null || excludedStatuses.isEmpty() || !excludedStatuses.contains(item.getStatus()))
                .collect(Collectors.toList());

        var result = new LinkedList<JPrivateListWishlistItem>();
        for (WishlistItem item: items) {
            var meta = wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item);
            result.add(JPrivateListWishlistItem.Converter.build(item, meta));
        }
        return result;
    }

    @GetMapping("{id}")
    public JPrivateWishlistItemDetails get(@PathVariable("id") String id, @AuthUser User user) {
        var item = wishlistItemRepository.findById(id).orElseThrow(notFoundSupplier(id));
        if (!item.getOwner().equals(user)) {
            throw unauthorized(id);
        }
        return JPrivateWishlistItemDetails.Converter.build(
                item,
                wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item));
    }

    @PostMapping("{id}/status")
    public JPrivateWishlistItemDetails setStatus(@PathVariable("id") String id,
                                                @RequestBody JSetStatusRequest req,
                                                @AuthUser User user) {
        var item = wishlistItemRepository.findById(id).orElseThrow(notFoundSupplier(id));
        if (!item.getOwner().equals(user)) {
            throw unauthorized(id);
        }

        if (!item.getStatus().canTransitionTo(req.getStatus())) {
            throw badRequest("Can't transition " + item.getStatus() + " -> " + req.getStatus());
        }

        item.setStatus(req.getStatus());
        item = wishlistItemRepository.save(item);

        return JPrivateWishlistItemDetails.Converter.build(
                item,
                wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item));
    }

    @PostMapping("{id}/is-public")
    public JPrivateWishlistItemDetails setIsPublic(@PathVariable("id") String id,
                                                  @RequestBody JSetIsPublicRequest req,
                                                  @AuthUser User user) {
        var item = wishlistItemRepository.findById(id).orElseThrow(notFoundSupplier(id));
        if (!item.getOwner().equals(user)) {
            throw unauthorized(id);
        }
        item.setPublic(req.isPublic());
        item = wishlistItemRepository.save(item);

        return JPrivateWishlistItemDetails.Converter.build(
                item,
                wishlistItemMetaRepository.findFirstByItemOrderByCreatedAtDesc(item));
    }

}
