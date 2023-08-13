package com.shopapotheke.githubpoprepo.adapter.in.rest;

import com.shopapotheke.githubpoprepo.adapter.in.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping
    public Mono<GithubResponse> getPopularRepos(){
        return githubService.getPopRepos().log();
    }

}
