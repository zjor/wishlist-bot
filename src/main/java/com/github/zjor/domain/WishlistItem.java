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

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishlist_items")
public class WishlistItem extends Born {

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    @Column(name = "url", length = 1024)
    private String url;

    @Column(name = "is_public", columnDefinition = "default false", nullable = false)
    private boolean isPublic;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> tags = new ArrayList<>();

}

