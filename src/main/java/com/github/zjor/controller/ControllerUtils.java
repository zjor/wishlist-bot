package com.github.zjor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

public class ControllerUtils {

    public static ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

    public static ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    public static ResponseStatusException unauthorized(String message) {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    }

    public static Supplier<ResponseStatusException> notFoundSupplier(String message) {
        return () -> notFound(message);
    }

    public static Supplier<ResponseStatusException> unauthorizedSupplier(String message) {
        return () -> unauthorized(message);
    }

}
