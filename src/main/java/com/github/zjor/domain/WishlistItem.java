package com.github.zjor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItem {

    private String id;
    private User owner;
    private String name;

    private String description;
    private String imageUrl;
    private String url;

    private List<String> tags;

}

