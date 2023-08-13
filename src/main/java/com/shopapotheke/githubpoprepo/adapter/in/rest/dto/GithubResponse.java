package com.shopapotheke.githubpoprepo.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GithubResponse(
        @JsonProperty("items")
        List<Items> items) {
}
