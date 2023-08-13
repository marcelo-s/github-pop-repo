package com.shopapotheke.githubpoprepo.adapter.in.rest;

import com.shopapotheke.githubpoprepo.adapter.in.exception.InputException;
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
                                          @RequestParam String language,
                                          @RequestParam int top) {
        validateDate(date);
        validateTop(top);
        GithubService.Arguments arguments = new GithubService.Arguments(date, language, top);
        return githubService.getPopularRepositories(arguments);
    }

    private void validateTop(int top) {
        if (!(top == 10 || top == 50 || top == 100)) {
            throw new InputException(
                    "Top value not allowed. Possible values: %s".formatted("10, 50, 100"));
        }
    }

    private void validateDate(String date) {
        if (!GenericValidator.isDate(date, "yyyy-MM-dd", true)) {
            throw new InputException(
                    "Date query param is not valid, is should follow the format: %s".formatted("yyyy-MM-dd"));
        }
    }

}
