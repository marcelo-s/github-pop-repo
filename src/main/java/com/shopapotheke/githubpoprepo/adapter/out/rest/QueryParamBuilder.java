package com.shopapotheke.githubpoprepo.adapter.out.rest;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class QueryParamBuilder {

    public String buildUriParams(String date, String language) {
        StringBuilder queryParams = new StringBuilder();
        queryParams.append(buildGithubQuery(date, language));
        queryParams.append("&sort=stars");
        queryParams.append("&order=desc");
        return queryParams.toString();
    }

    private String buildGithubQuery(String date, String language) {
        StringBuilder githubQueries = new StringBuilder();
        githubQueries.append("q=");
        githubQueries.append("created:>").append(date);
        githubQueries.append(" ");
        githubQueries.append("language:").append(language);
        return githubQueries.toString();
    }

    private String encode(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }
}
