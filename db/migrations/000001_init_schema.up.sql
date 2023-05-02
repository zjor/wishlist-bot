create table if not exists users (
    id varchar(36) not null primary key,
    created_at timestamp(6) with time zone not null,
    ext_id varchar(255) not null
        constraint uk_i4ho3crwxga4wlek1frlauu9x unique,
    first_name varchar(255),
    last_name  varchar(255),
    username   varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

alter table users
    owner to wishlist_bot;

create unique index if not exists users_pkey
    on users (id);

create unique index if not exists uk_i4ho3crwxga4wlek1frlauu9x
    on users (ext_id);

create unique index if not exists uk_r43af9ap4edm43mmtq01oddj6
    on users (username);

create table wishlist_items (
    id varchar(36) not null
        primary key,
    created_at  timestamp(6) with time zone not null,
    description varchar(255),
    image_url   varchar(255),
    name        varchar(255) not null,
    tags        jsonb,
    url         varchar(255),
    owner_id    varchar(36) not null
        constraint fk1k8x0w6f5l1hgwqbmglq38j8c
            references users
);

alter table wishlist_items
    owner to wishlist_bot;

create unique index if not exists wishlist_items_pkey
    on wishlist_items (id);

