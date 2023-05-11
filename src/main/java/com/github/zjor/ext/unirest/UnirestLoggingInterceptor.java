package com.github.zjor.ext.unirest;

import kong.unirest.Config;
import kong.unirest.HttpRequest;
import kong.unirest.Interceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnirestLoggingInterceptor implements Interceptor {
    @Override
    public void onRequest(HttpRequest<?> request, Config config) {
        log.info("Request: {} {}", request.getHttpMethod(), request.getUrl());
    }
}
