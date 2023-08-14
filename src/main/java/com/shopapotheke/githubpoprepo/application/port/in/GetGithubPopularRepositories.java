package com.shopapotheke.githubpoprepo.application.port.in;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;

public interface GetGithubPopularRepositories {

    GithubResponse getPopularRepositories(Arguments arguments);

    record Arguments(String date, String language, int top) {
    }
}
