ALTER TABLE wishlist_items ADD COLUMN "status" TEXT NOT NULL DEFAULT 'OPEN';
ALTER TABLE wishlist_items ADD COLUMN "price" NUMERIC(8, 2);
ALTER TABLE wishlist_items ADD COLUMN "currency" VARCHAR(64);