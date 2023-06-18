package com.github.zjor.controller.dto;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.domain.WishlistItemMeta;
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
public class JWishlistItem {

    private String id;
    private String ownerId;
    private String name;
    private String description;
    private String imageUrl;
    private String url;
    private List<String> tags;
    private boolean isPublic;
    private WishlistItem.Status status;
    private BigDecimal price;
    private String currency;
    private String createdAt;

    public static class Convert {
        public static JWishlistItem build(WishlistItem item, Optional<WishlistItemMeta> meta) {
            var builder = JWishlistItem.builder()
                    .id(item.getId())
                    .ownerId(item.getOwner().getId())
                    .name(item.getName())
                    .description(item.getDescription())
                    .imageUrl(item.getImageUrl())
                    .url(item.getUrl())
                    .tags(item.getTags())
                    .isPublic(item.isPublic())
                    .status(item.getStatus())
                    .price(item.getPrice())
                    .currency(item.getCurrency())
                    .createdAt(item.getCreatedAt().toString());
            meta.ifPresent(m -> builder.imageUrl(m.getImageUrl()));
            return builder.build();
        }
    }
}

