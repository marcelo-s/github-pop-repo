package com.shopapotheke.githubpoprepo.adapter.out.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Item(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("language")
        String language,
        @JsonProperty("html_url")
        String htmlUrl,
        @JsonProperty("stargazers_count")
        int stargazersCount,
        @JsonProperty("created_at")
        String createdAt
) {
}
