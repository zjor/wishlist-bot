package com.github.zjor.controller.dto;

import com.github.zjor.domain.WishlistItem;
import com.github.zjor.domain.WishlistItemMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWishlistItem {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String url;
    private List<String> tags;
    private boolean isPublic;
    private String createdAt;
    // TODO: return userId

    public static class Convert {
        public static JWishlistItem build(WishlistItem item, Optional<WishlistItemMeta> meta) {
            var builder = JWishlistItem.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .description(item.getDescription())
                    .imageUrl(item.getImageUrl())
                    .url(item.getUrl())
                    .tags(item.getTags())
                    .isPublic(item.isPublic())
                    .createdAt(item.getCreatedAt().toString());
            meta.ifPresent(m -> builder.imageUrl(m.getImageUrl()));
            return builder.build();
        }
    }
}

