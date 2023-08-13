package com.shopapotheke.githubpoprepo.adapter.out.rest;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.out.GithubRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class GithubRestClientImpl implements GithubRestClient {

    private final QueryParamBuilder queryParamBuilder;

    public static final String BASE_GITHUB_URL = "https://api.github.com/search/repositories";
    private final RestTemplate restTemplate;

    @Override
    public GithubResponse fetchPopularRepositories(String date, String language) {
        String urlWithParams = buildUrlWithQueryParams(date, language);
        ResponseEntity<GithubResponse> response =
                restTemplate.getForEntity(
                        urlWithParams,
                        GithubResponse.class
                                         );
        return response.getBody();
    }

    private String buildUrlWithQueryParams(String date, String language) {
        String queryParams = queryParamBuilder.buildUriParams(date, language);
        return BASE_GITHUB_URL + "?" + queryParams;
    }

}
