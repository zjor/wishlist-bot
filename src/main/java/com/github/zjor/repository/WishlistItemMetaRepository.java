package com.github.zjor.repository;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.domain.WishlistItemMeta;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface WishlistItemMetaRepository extends CrudRepository<WishlistItemMeta, String> {

    Optional<WishlistItemMeta> findFirstByItemOrderByCreatedAtDesc(WishlistItem item);

}
