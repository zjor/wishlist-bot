CREATE UNIQUE INDEX IF NOT EXISTS uk_r43af9ap4edm43mmtq01oddj6
    ON users (username);

ALTER TABLE users ALTER COLUMN username SET NOT NULL;