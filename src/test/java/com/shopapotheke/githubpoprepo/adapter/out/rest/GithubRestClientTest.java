package com.shopapotheke.githubpoprepo.adapter.out.rest;

import com.shopapotheke.githubpoprepo.adapter.in.exception.RestException;
import com.shopapotheke.githubpoprepo.adapter.out.rest.dto.GithubResponse;
import com.shopapotheke.githubpoprepo.adapter.out.rest.service.RestService;
import com.shopapotheke.githubpoprepo.application.port.in.GithubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class GithubRestClientTest {

    @Spy
    private final QueryParamBuilder queryParamBuilder = new QueryParamBuilder();
    @Mock
    private RestTemplate restTemplate;
    @Spy
    private RestService restService = new RestService();

    @InjectMocks
    private GithubRestClient githubRestClient;

    @Test
    void shouldCallGithubAndReturnResponse() {
        //given
        GithubService.Arguments arguments = new GithubService.Arguments("2000-01-01", "Java", 10);
        GithubResponse mockResponse = new GithubResponse(10, Collections.emptyList());
        given(restTemplate.getForEntity(anyString(), eq(GithubResponse.class)))
                .willReturn(ResponseEntity.ok(mockResponse));

        //when
        GithubResponse actual = githubRestClient.fetchPopularRepositories(arguments);

        //then
        assertThat(actual).isEqualTo(mockResponse);
        then(restTemplate).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrowExceptionWhenCallToGithubReturnsException() {
        //given
        GithubService.Arguments arguments = new GithubService.Arguments("2000-01-01", "Java", 10);
        given(restTemplate.getForEntity(anyString(), eq(GithubResponse.class)))
                .willThrow(new RuntimeException("Some exception"));

        //when
        Throwable thrown = catchThrowable(() -> {
            githubRestClient.fetchPopularRepositories(arguments);
        });

        //then
        assertThat(thrown)
                .isInstanceOf(RestException.class)
                .hasMessageContaining("RestClientException during call to Github");
    }
}