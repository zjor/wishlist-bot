package com.github.zjor.controller.dto;

import com.github.zjor.domain.ItemStatus;
import com.github.zjor.domain.WishlistItem;
import com.github.zjor.domain.WishlistItemMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

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
    private ItemStatus status;
    private BigDecimal price;
    private String currency;
    private String createdAt;

    private List<Pair<ItemStatus, Boolean>> allowedStatuses;

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

            builder.allowedStatuses(item.getStatus().getAllowedStatuses());

            meta.ifPresent(m -> builder.imageUrl(m.getImageUrl()));
            return builder.build();
        }
    }
}

