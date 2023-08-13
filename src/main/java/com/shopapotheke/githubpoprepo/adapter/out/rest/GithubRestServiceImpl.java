package com.shopapotheke.githubpoprepo.adapter.out.rest;

import com.shopapotheke.githubpoprepo.adapter.in.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.adapter.in.rest.dto.Items;
import com.shopapotheke.githubpoprepo.application.port.out.GithubRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Comparator;


@Service
@RequiredArgsConstructor
public class GithubRestServiceImpl implements GithubRestService {

    private final WebClient webClient;

    @Override
    public Mono<GithubResponse> getPopRepos() {
        String githubUrl = "https://api.github.com/search/repositories?q=created:>2019-01-10&sort=stars&order=asc";
        Mono<GithubResponse> block = webClient.get()
                                              .uri(githubUrl)
                                              .retrieve()
                                              .bodyToMono(GithubResponse.class)
                                              .doOnNext(githubResponse -> githubResponse.items().sort(Comparator.comparing(
                                                Items::id)));
        return block;
    }
}
