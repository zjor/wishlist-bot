package com.github.zjor.integrations.opengraph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenGraphResponse {

    @JsonProperty("hybridGraph")
    private HybridGraph hybridGraph;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HybridGraph {

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("type")
        private String type;

        @JsonProperty("image")
        private String image;

        @JsonProperty("url")
        private String url;

        @JsonProperty("favicon")
        private String favicon;

        @JsonProperty("site_name")
        private String siteName;

        @JsonProperty("articlePublishedTime")
        private String articlePublishedTime;

        @JsonProperty("articleAuthor")
        private String articleAuthor;
    }
}
