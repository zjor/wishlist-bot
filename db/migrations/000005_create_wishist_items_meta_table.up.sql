CREATE TABLE IF NOT EXISTS wishlist_items_meta
(
    id          VARCHAR(36) NOT NULL PRIMARY KEY,
    item_id     VARCHAR(36) NOT NULL,
    title       VARCHAR(1024),
    description TEXT,
    image_url   VARCHAR(1024),
    raw         JSONB
);

ALTER TABLE wishlist_items_meta
    ADD CONSTRAINT wishlist_items_meta_item_id_fkey
        FOREIGN KEY (item_id) REFERENCES wishlist_items (id)
            ON DELETE CASCADE;