package com.shopapotheke.githubpoprepo.application.port.in;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;

public interface GithubService {

    GithubResponse getPopRepos();
}
