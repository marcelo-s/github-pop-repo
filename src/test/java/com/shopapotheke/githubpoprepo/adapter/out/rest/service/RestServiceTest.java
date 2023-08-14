package com.shopapotheke.githubpoprepo.adapter.out.rest.service;

import com.shopapotheke.githubpoprepo.adapter.out.rest.GithubRestClient;
import com.shopapotheke.githubpoprepo.application.port.in.GetGithubPopularRepositories;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class RestServiceTest {

    @Spy
    private RestService restService = new RestService();
    @Mock
    private GithubRestClient githubRestClient;

    @Test
    void shouldRetry() {
        //given
        GetGithubPopularRepositories.Arguments dummyArguments = new GetGithubPopularRepositories.Arguments("2000-01-01", "Java", 10);
        String exceptionMessage = "Rest exception";
        given(githubRestClient.fetchPopularReposFromGithub(dummyArguments))
                .willThrow(new RuntimeException(exceptionMessage));

        CircuitBreakerConfig config = CircuitBreakerConfig
                .custom()
                .minimumNumberOfCalls(10)
                .build();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        CircuitBreaker circuitBreaker = registry.circuitBreaker(RestService.class.getName() + "CircuitBreaker");
        given(restService.getCircuitBreaker()).willReturn(circuitBreaker);

        //when
        Throwable thrown = catchThrowable(
                () -> restService.executeRestCall(() -> githubRestClient.fetchPopularReposFromGithub(dummyArguments))
                                         );

        //then
        assertThat(thrown).isInstanceOf(RuntimeException.class)
                          .hasMessage(exceptionMessage);
        then(githubRestClient).should(times(3)).fetchPopularReposFromGithub(dummyArguments);
    }

    @Test
    void shouldOpenCircuitBreaker() {
        //given
        GetGithubPopularRepositories.Arguments dummyArguments = new GetGithubPopularRepositories.Arguments("2000-01-01", "Java", 10);

        given(githubRestClient.fetchPopularReposFromGithub(dummyArguments))
                .willThrow(new RuntimeException("testing me"));

        //when
        Throwable thrown = catchThrowable(
                () -> restService.executeRestCall(() -> githubRestClient.fetchPopularReposFromGithub(dummyArguments)));

        //then
        assertThat(thrown).isInstanceOf(CallNotPermittedException.class)
                          .hasMessageContaining("is OPEN and does not permit further calls");
        then(githubRestClient).should(times(2)).fetchPopularReposFromGithub(dummyArguments);
    }
}