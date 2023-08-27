ALTER TABLE wishlist_items
    DROP COLUMN IF EXISTS "thumbnail_url";

ALTER TABLE wishlist_items_meta
    DROP COLUMN IF EXISTS "thumbnail_url";