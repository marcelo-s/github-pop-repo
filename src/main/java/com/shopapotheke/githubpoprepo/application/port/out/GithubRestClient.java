package com.shopapotheke.githubpoprepo.application.port.out;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;

public interface GithubRestClient {

    GithubResponse fetchPopularRepositories(String date, String language);
}
