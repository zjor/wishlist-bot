CREATE TABLE IF NOT EXISTS wl_item_reservations
(
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    item_id     VARCHAR(36)  NOT NULL
        REFERENCES wishlist_items (id) ON DELETE CASCADE,
    reserved_by VARCHAR(36)  NOT NULL
        REFERENCES users (id) ON DELETE CASCADE,
    note        TEXT,
    created_at  TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP(3)
);