package com.github.zjor.repository;

import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WishlistItemRepository {

    WishlistItem create(User owner, String name, String description, String imageUrl, String url, List<String> tags);

    List<WishlistItem> findByOwner(User owner);
    List<WishlistItem> findByOwnerAndTags(User owner, Set<String> tags);

    Optional<WishlistItem> get(String id);
}
