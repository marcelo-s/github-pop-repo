package com.shopapotheke.githubpoprepo.application.usecase;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GetGithubPopularRepositories;
import com.shopapotheke.githubpoprepo.application.port.out.GithubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetGithubPopularRepositoriesUseCase implements GetGithubPopularRepositories {

    private final GithubClient githubRestClient;

    @Override
    public GithubResponse getPopularRepositories(Arguments arguments) {
        return githubRestClient.fetchPopularRepositories(arguments);
    }
}
