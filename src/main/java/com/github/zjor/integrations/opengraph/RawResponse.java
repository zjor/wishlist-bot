package com.github.zjor.integrations.opengraph;

import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RawResponse<T> {

    public final boolean success;
    public final String raw;
    public final Optional<T> data;

    public static <T> RawResponse<T> of(String raw, T data) {
        return new RawResponse(true, raw, Optional.of(data));
    }

    public static <T> RawResponse<T> of(String raw) {
        return new RawResponse(false, raw, Optional.empty());
    }

}
