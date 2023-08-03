package com.github.zjor.controller.dto;

import com.github.zjor.domain.ItemStatus;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.domain.WishlistItemMeta;
import com.github.zjor.domain.jooq.tables.records.WishlistItemsMetaRecord;
import com.github.zjor.domain.jooq.tables.records.WishlistItemsRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JPublicListWishlistItem {

    private String id;
    private JUser owner;
    private String name;
    private String description;
    private String imageUrl;
    private String url;
    private List<String> tags;
    private ItemStatus status;
    private BigDecimal price;
    private String currency;
    private String createdAt;

    public static class Converter {
        public static JPublicListWishlistItem build(WishlistItem item, Optional<WishlistItemMeta> meta) {
            var builder = JPublicListWishlistItem.builder()
                    .id(item.getId())
                    .owner(JUser.Converter.build(item.getOwner()))
                    .name(item.getName())
                    .description(item.getDescription())
                    .imageUrl(item.getImageUrl())
                    .url(item.getUrl())
                    .tags(item.getTags())
                    .status(item.getStatus())
                    .price(item.getPrice())
                    .currency(item.getCurrency())
                    .createdAt(item.getCreatedAt().toString());

            meta.ifPresent(m -> builder.imageUrl(m.getImageUrl()));
            return builder.build();
        }

        public static JPublicListWishlistItem build(WishlistItemsRecord item, Optional<WishlistItemsMetaRecord> meta) {
            var builder = JPublicListWishlistItem.builder()
                    .id(item.getId())
                    .owner(null)
                    .name(item.getName())
                    .description(item.getDescription())
                    .imageUrl(item.getImageUrl())
                    .url(item.getUrl())
                    .tags(List.of()) // TODO: impl
                    .status(ItemStatus.valueOf(item.getStatus()))
                    .price(item.getPrice())
                    .currency(item.getCurrency())
                    .createdAt(item.getCreatedAt().toString());
            meta.ifPresent(m -> builder.imageUrl(m.getImageUrl()));
            return builder.build();
        }
    }
}

