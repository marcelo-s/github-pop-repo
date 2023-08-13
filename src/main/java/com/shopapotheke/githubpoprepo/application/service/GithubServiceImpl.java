package com.shopapotheke.githubpoprepo.application.service;

import com.shopapotheke.githubpoprepo.adapter.in.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import com.shopapotheke.githubpoprepo.application.port.out.GithubRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {

    private final GithubRestService githubRestService;

    @Override
    public Mono<GithubResponse> getPopRepos() {
        return githubRestService.getPopRepos();
    }
}
