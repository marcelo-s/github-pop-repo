package com.shopapotheke.githubpoprepo.adapter.out.rest;

import com.shopapotheke.githubpoprepo.adapter.in.exception.RestException;
import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.adapter.out.rest.service.RestService;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import com.shopapotheke.githubpoprepo.application.port.out.GithubClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class GithubRestClient implements GithubClient {

    public static final String BASE_GITHUB_URL = "https://api.github.com/search/repositories";

    private final QueryParamBuilder queryParamBuilder;
    private final RestTemplate restTemplate;
    private final RestService restService;

    @Override
    public GithubResponse fetchPopularRepositories(GithubService.Arguments arguments) {
        try {
            return restService.executeRestCall(() -> fetchPopularReposFromGithub(arguments));
        } catch (Exception e) {
            String errorMessage = "RestClientException during call to Github";
            log.error(errorMessage, e);
            throw new RestException(errorMessage, e);
        }
    }

    public GithubResponse fetchPopularReposFromGithub(GithubService.Arguments arguments) {
        String urlWithParams = buildUrlWithQueryParams(arguments);
        ResponseEntity<GithubResponse> response =
                restTemplate.getForEntity(
                        urlWithParams,
                        GithubResponse.class
                                         );
        return response.getBody();
    }

    private String buildUrlWithQueryParams(GithubService.Arguments arguments) {
        String queryParams = queryParamBuilder.buildUriParams(arguments);
        return BASE_GITHUB_URL + "?" + queryParams;
    }

}
