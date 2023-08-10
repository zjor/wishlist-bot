package com.github.zjor.repository.jooq;

import com.github.zjor.domain.jooq.tables.records.WishlistItemsMetaRecord;
import com.github.zjor.domain.jooq.tables.records.WishlistItemsRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

import static com.github.zjor.domain.jooq.tables.WishlistItems.WISHLIST_ITEMS;
import static com.github.zjor.domain.jooq.tables.WishlistItemsMeta.WISHLIST_ITEMS_META;
import static org.jooq.impl.DSL.and;

//TODO: don't use JOOQ until issues with ConnectionPool are solved
public class WishlistItemJooqRepo {

    private final DSLContext dsl;

    public WishlistItemJooqRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<WishlistItemsRecord> getPublicItemsByUserId(String userId) {
        return dsl.selectFrom(WISHLIST_ITEMS)
                .where(and(
                        WISHLIST_ITEMS.OWNER_ID.eq(userId),
                        WISHLIST_ITEMS.IS_PUBLIC.eq(true)))
                .orderBy(WISHLIST_ITEMS.CREATED_AT.desc())
                .fetch();
    }

    public Optional<WishlistItemsMetaRecord> getItemMetaByItemId(String itemId) {
        return dsl.selectFrom(WISHLIST_ITEMS_META)
                .where(WISHLIST_ITEMS_META.ITEM_ID.eq(itemId))
                .orderBy(WISHLIST_ITEMS_META.CREATED_AT.desc())
                .limit(1)
                .stream().findFirst();
    }

}
