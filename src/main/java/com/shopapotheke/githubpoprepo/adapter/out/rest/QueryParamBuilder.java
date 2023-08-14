package com.shopapotheke.githubpoprepo.adapter.out.rest;

import com.shopapotheke.githubpoprepo.application.port.in.GetGithubPopularRepositories;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class QueryParamBuilder {

    public String buildUriParams(GetGithubPopularRepositories.Arguments arguments) {
        StringBuilder queryParams = new StringBuilder();
        queryParams.append(buildGithubQuery(arguments.date(), arguments.language()));
        queryParams.append("&sort=stars");
        queryParams.append("&order=desc");
        queryParams.append("&per_page=").append(arguments.top());
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
