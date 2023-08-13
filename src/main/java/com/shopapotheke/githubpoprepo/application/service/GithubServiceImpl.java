package com.shopapotheke.githubpoprepo.application.service;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import com.shopapotheke.githubpoprepo.application.port.out.GithubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {

    private final GithubClient githubRestClient;

    @Override
    public GithubResponse getPopularRepositories(Arguments arguments) {
        return githubRestClient.fetchPopularRepositories(arguments);
    }
}
