package com.github.zjor.integrations.opengraph;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * A client for https://www.opengraph.io/
 */
@Slf4j
public class OpenGraphClient {
    private static final String BASE_URL = "https://opengraph.io/api/1.1/site/";

    private final String apiKey;

    private final ObjectMapper mapper = new ObjectMapper();

    public OpenGraphClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public RawResponse<OpenGraphResponse> fetchOpenGraph(String url, boolean render) {
        log.info("url: {}", url);
        try {
            var response = Unirest.get(BASE_URL + URLEncoder.encode(url, Charset.forName("UTF-8")))
                    .queryString("app_id", apiKey)
                    .queryString("cache_ok", "yes")
                    .queryString("full_render", render ? "yes" : "no")
                    .asJson();
            var json = response.getBody().toString();

            // TODO: add global logging interceptor
            log.info("<= {}: {}", response.getStatus(), json);
            if (response.isSuccess()) {
                return RawResponse.success(json, mapper.readValue(json, OpenGraphResponse.class));
            } else {
                return RawResponse.error(json);
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to fetch open graph: " + e.getMessage(), e);
            return RawResponse.error(null, e);
        }
    }

}
