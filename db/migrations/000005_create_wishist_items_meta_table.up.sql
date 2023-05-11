CREATE TABLE IF NOT EXISTS wishlist_items_meta
(
    id            VARCHAR(36)                 NOT NULL PRIMARY KEY,
    item_id       VARCHAR(36)                 NOT NULL,
    title         VARCHAR(1024),
    description   TEXT,
    image_url     VARCHAR(1024),
    raw           JSONB,
    created_at    TIMESTAMP(6) WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_error      BOOLEAN                     NOT NULL DEFAULT FALSE,
    error_message VARCHAR(1024),
    retryable     BOOLEAN                     NOT NULL DEFAULT FALSE
);

ALTER TABLE wishlist_items_meta
    ADD CONSTRAINT wishlist_items_meta_item_id_fkey
        FOREIGN KEY (item_id) REFERENCES wishlist_items (id)
            ON DELETE CASCADE;