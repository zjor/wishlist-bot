package com.github.zjor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

public class ControllerUtils {

    public static ResponseStatusException badRequest(String message) {
        return new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), message);
    }

    public static ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), message);
    }

    public static Supplier<ResponseStatusException> notFoundSupplier(String message) {
        return () -> notFound(message);
    }

}
