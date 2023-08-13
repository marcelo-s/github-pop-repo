package com.shopapotheke.githubpoprepo.adapter.in.rest;

import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping
    public GithubResponse getPopularRepos(@RequestParam String date,
                                          @RequestParam String language) {
        validateDate(date);
        return githubService.getPopularRepositories(date, language);
    }

    private void validateDate(String date) {
        if (!GenericValidator.isDate(date, "yyyy-MM-dd", true)) {
            throw new RuntimeException(
                    "Date query param is not valid, is should follow the format: %s".formatted("yyyy-MM-dd"));
        }
    }

}
