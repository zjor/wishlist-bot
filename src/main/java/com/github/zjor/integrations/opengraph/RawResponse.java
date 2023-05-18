package com.github.zjor.integrations.opengraph;

import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class RawResponse<T> {

    public final boolean success;

    public final String raw;
    public final Optional<T> data;

    public final Optional<Exception> exception;

    public static <T> RawResponse<T> success(String raw, T data) {
        return new RawResponse(true, raw, Optional.of(data), Optional.empty());
    }

    public static <T> RawResponse<T> error(String raw, Exception exception) {
        return new RawResponse(false, raw, Optional.empty(), Optional.of(exception));
    }

    public static <T> RawResponse<T> error(String raw) {
        return new RawResponse(false, raw, Optional.empty(), Optional.empty());
    }

}
