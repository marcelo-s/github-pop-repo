package com.shopapotheke.githubpoprepo.adapter.out.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Items(
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("description")
        String description,
        @JsonProperty("html_url")
        String htmlUrl,
        @JsonProperty("stargazers_count")
        int stargazersCount,
        @JsonProperty("created_at")
        String createdAt
) {
}
