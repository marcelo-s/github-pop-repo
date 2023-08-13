package com.shopapotheke.githubpoprepo.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Items(
        @JsonProperty("id")
        String id
) {
}
