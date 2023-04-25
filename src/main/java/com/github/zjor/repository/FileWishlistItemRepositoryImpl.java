package com.github.zjor.repository;

import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FileWishlistItemRepositoryImpl implements WishlistItemRepository {
    @Override
    public WishlistItem create(User owner, String name, String description, String imageUrl, String url, List<String> tags) {
        return null;
    }

    @Override
    public List<WishlistItem> findByOwner(User owner) {
        return null;
    }

    @Override
    public List<WishlistItem> findByOwnerAndTags(User owner, Set<String> tags) {
        return null;
    }

    @Override
    public Optional<WishlistItem> get(String id) {
        return Optional.empty();
    }
}
