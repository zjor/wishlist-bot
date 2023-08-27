package com.github.zjor.domain;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishlist_items_meta")
public class WishlistItemMeta extends Born {

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private WishlistItem item;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Type(JsonType.class)
    @Column(name = "raw", columnDefinition = "jsonb")
    private String raw;

    @Column(name = "is_error", columnDefinition = "default false", nullable = false)
    private boolean isError;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "retryable", columnDefinition = "default false", nullable = false)
    private boolean retryable;
}

