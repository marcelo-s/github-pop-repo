package com.shopapotheke.githubpoprepo.adapter.out.rest;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import com.shopapotheke.githubpoprepo.application.port.out.GithubClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class GithubRestClient implements GithubClient {

    private final QueryParamBuilder queryParamBuilder;

    public static final String BASE_GITHUB_URL = "https://api.github.com/search/repositories";
    private final RestTemplate restTemplate;

    @Override
    public GithubResponse fetchPopularRepositories(GithubService.Arguments arguments) {
        try {
            String urlWithParams = buildUrlWithQueryParams(arguments);
            ResponseEntity<GithubResponse> response =
                    restTemplate.getForEntity(
                            urlWithParams,
                            GithubResponse.class
                                             );
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Error response from Github service", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Unexpected error during rest call to Github", e);
            throw new RuntimeException(e);
        }
    }

    private String buildUrlWithQueryParams(GithubService.Arguments arguments) {
        String queryParams = queryParamBuilder.buildUriParams(arguments);
        return BASE_GITHUB_URL + "?" + queryParams;
    }

}
