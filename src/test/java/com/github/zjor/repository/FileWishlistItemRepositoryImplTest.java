package com.github.zjor.repository;


import com.github.zjor.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

public class FileWishlistItemRepositoryImplTest {

    private File wishlistItemsTempFile;
    private File userTempFile;
    private UserRepository userRepository;
    private WishlistItemRepository wishlistItemRepository;
    private User alice, bob;

    @Before
    public void setUp() throws Exception {
        wishlistItemsTempFile = File.createTempFile("wishlistItems", ".json");
        userTempFile = File.createTempFile("users", ".json");
        userRepository = new FileUserRepositoryImpl(userTempFile.getAbsolutePath());
        wishlistItemRepository = new FileWishlistItemRepositoryImpl(wishlistItemsTempFile.getAbsolutePath());

        alice = userRepository.create("123", "alice.brown", "Alice", "Brown");
        bob = userRepository.create("456", "bob.smith", "Bob", "Smith");
    }

    @Test
    public void shouldCreateAndFindWishlistItem() {
        var item = wishlistItemRepository.create(alice, "item1", "desc1", "url1", "image1", List.of("book"));
        Assert.notNull(item.getId(), "id should be generated");

        var found = wishlistItemRepository.findByOwner(alice);
        Assert.isTrue(found.size() > 0, "wishlist item should be found for Alice");

        var notFoundForBob = wishlistItemRepository.findByOwner(bob);
        Assert.isTrue(notFoundForBob.size() == 0, "wishlist item should not be found for Bob");

        var foundById = wishlistItemRepository.get(item.getId());
        Assert.isTrue(foundById.isPresent(), "wishlist item should be found by id");
    }

}