package com.github.zjor.repository;

import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.util.IdGenerator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FileWishlistItemRepositoryImpl extends AbstractFileRepository<WishlistItem> implements WishlistItemRepository {

    public FileWishlistItemRepositoryImpl(String filename) {
        super(WishlistItem.class, filename);
    }

    @Override
    public WishlistItem create(User owner, String name, String description, String imageUrl, String url, List<String> tags) {
        var item = WishlistItem.builder()
                .id(IdGenerator.nextId())
                .owner(owner)
                .name(name)
                .description(description)
                .imageUrl(imageUrl)
                .url(url)
                .tags(tags)
                .build();
        addItem(item);
        return item;
    }

    @Override
    public List<WishlistItem> findByOwner(User owner) {
        return getItems().stream().filter(i -> i.getOwner().equals(owner)).toList();
    }

    @Override
    public List<WishlistItem> findByOwnerAndTags(User owner, Set<String> tags) {
        return getItems().stream().filter(i -> i.getOwner().equals(owner) && i.getTags().containsAll(tags)).toList();
    }

    @Override
    public Optional<WishlistItem> get(String id) {
        return getItems().stream().filter(i -> i.getId().equals(id)).findFirst();
    }
}
