package com.shopapotheke.githubpoprepo.application.port.in;

import com.shopapotheke.githubpoprepo.adapter.in.rest.dto.GithubResponse;
import reactor.core.publisher.Mono;

public interface GithubService {

    public Mono<GithubResponse> getPopRepos();
}
