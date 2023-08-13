package com.shopapotheke.githubpoprepo.application.port.out;

import com.shopapotheke.githubpoprepo.adapter.in.rest.dto.GithubResponse;
import reactor.core.publisher.Mono;

public interface GithubRestService {

    public Mono<GithubResponse> getPopRepos();
}
