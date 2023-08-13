package com.shopapotheke.githubpoprepo.adapter.out.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GithubResponse(
        @JsonProperty("total_count")
        Integer totalCount,
        @JsonProperty("items")
        List<Items> items) {
}
