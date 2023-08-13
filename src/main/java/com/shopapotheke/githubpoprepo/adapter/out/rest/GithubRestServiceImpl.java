package com.shopapotheke.githubpoprepo.adapter.out.rest;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.out.GithubRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class GithubRestServiceImpl implements GithubRestService {

    public static final String GITHUB_URL
            = "https://api.github.com/search/repositories?q=created:>2019-01-10&sort=stars&order=asc";
    private final RestTemplate restTemplate;

    @Override
    public GithubResponse getPopRepos() {
        ResponseEntity<GithubResponse> response =
                restTemplate.getForEntity(
                        GITHUB_URL,
                        GithubResponse.class);
        return response.getBody();
    }
}
