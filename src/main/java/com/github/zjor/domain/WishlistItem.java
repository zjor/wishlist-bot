package com.github.zjor.domain;

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
public class WishlistItem {

    public record Price(BigDecimal price, String currency) {
    }

    public enum Status {
        ACTIVE,
        ARCHIVED,
        DONE,
        RESERVED
    }

    private int id;
    private User owner;
    private String name;

    private String description;
    private String imageUrl;
    private String url;

    //    ???
    private List<String> tags;
    private Price price;
    private String priority;
    private Status status;
    private boolean isPublic;
    private Optional<WishlistItem> inspiredBy;


}

