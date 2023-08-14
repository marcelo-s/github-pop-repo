package com.shopapotheke.githubpoprepo.adapter.in.rest;

import com.shopapotheke.githubpoprepo.adapter.in.exception.InputException;
import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.application.port.in.GetGithubPopularRepositories;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.shopapotheke.githubpoprepo.adapter.in.rest.InputConstants.DATE_PATTERN;
import static com.shopapotheke.githubpoprepo.adapter.in.rest.InputConstants.TOP_VALUES;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pop")
public class GithubController {

    public static final String DEFAULT_DATE = "2023-01-01";
    public static final String DEFAULT_LANGUAGE = "java";
    public static final String DEFAULT_TOP = "10";
    private final GetGithubPopularRepositories getGithubPopularRepositories;

    @GetMapping
    public GithubResponse getPopularRepos(@RequestParam(defaultValue = DEFAULT_DATE) String date,
                                          @RequestParam(defaultValue = DEFAULT_LANGUAGE) String language,
                                          @RequestParam(defaultValue = DEFAULT_TOP) int top) {
        validateDate(date);
        validateTop(top);
        GetGithubPopularRepositories.Arguments arguments = new GetGithubPopularRepositories.Arguments(date, language, top);
        return getGithubPopularRepositories.getPopularRepositories(arguments);
    }

    private void validateTop(int top) {
        if (!TOP_VALUES.contains(top)) {
            throw new InputException(
                    "Top value not allowed. Possible values: %s".formatted("10, 50, 100"));
        }
    }

    private void validateDate(String date) {
        if (!GenericValidator.isDate(date, DATE_PATTERN, true)) {
            throw new InputException(
                    "Date query param is not valid, is should follow the format: %s".formatted("yyyy-MM-dd"));
        }
    }

}
