package com.shopapotheke.githubpoprepo.application.port.out;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GetGithubPopularRepositories;

public interface GithubClient {

    GithubResponse fetchPopularRepositories(GetGithubPopularRepositories.Arguments arguments);
}
