package com.shopapotheke.githubpoprepo.application.port.in;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;

public interface GithubService {

    GithubResponse getPopularRepositories(Arguments arguments);

    record Arguments(String date, String language, int top) {
    }
}
