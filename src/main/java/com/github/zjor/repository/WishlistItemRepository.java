package com.github.zjor.repository;

import com.github.zjor.domain.User;
import com.github.zjor.domain.WishlistItem;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface WishlistItemRepository extends CrudRepository<WishlistItem, String> {

    List<WishlistItem> findWishlistItemByOwnerOrderByCreatedAtDesc(User user);

    List<WishlistItem> findWishlistItemByIsPublicOrderByCreatedAtDesc(boolean isPublic);

    List<WishlistItem> findByOwnerAndIsPublicOrderByCreatedAtDesc(User user, boolean isPublic);

    default List<WishlistItem> findByOwner(User user) {
        return findWishlistItemByOwnerOrderByCreatedAtDesc(user);
    }

    default List<WishlistItem> findPublicByOwner(User user) {
        return findByOwnerAndIsPublicOrderByCreatedAtDesc(user, true);
    }
}
